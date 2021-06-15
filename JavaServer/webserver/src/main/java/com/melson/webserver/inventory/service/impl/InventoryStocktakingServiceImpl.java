package com.melson.webserver.inventory.service.impl;

import com.melson.base.utils.DateUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IStorageBatchRepository;
import com.melson.webserver.dict.dao.IStorageDetailRepository;
import com.melson.webserver.dict.entity.StorageBatch;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.inventory.dao.IInventoryStocktakingDetailRepository;
import com.melson.webserver.inventory.dao.IInventoryStocktakingRepository;
import com.melson.webserver.inventory.entity.InventoryStocktaking;
import com.melson.webserver.inventory.entity.InventoryStocktakingDetail;
import com.melson.webserver.inventory.service.IInventoryStocktakingService;
import com.melson.webserver.inventory.vo.InventoryStocktakingDetailVo;
import com.melson.webserver.inventory.vo.InventoryStocktakingVo;
import com.melson.webserver.inventory.vo.StorageBatchTakingVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 盘点接口实现类
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
@Service
public class InventoryStocktakingServiceImpl implements IInventoryStocktakingService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryStocktakingServiceImpl.class);

    @Autowired
    private IInventoryStocktakingRepository inventoryStocktakingRepository;
    @Autowired
    private IInventoryStocktakingDetailRepository inventoryStocktakingDetailRepository;
    @Autowired
    private IStorageDetailRepository storageDetailRepository;
    @Autowired
    private IStorageBatchRepository storageBatchRepository;

    @Override
    public List<InventoryStocktakingVo> list(Date date) {
        List<InventoryStocktaking> list = inventoryStocktakingRepository.findByCreateDateAfter(date);
        if (list == null || list.isEmpty()) {
            logger.error("盘点单为空");
            return null;
        }
        List<InventoryStocktakingVo> voList = new ArrayList<>();
        list.forEach(form -> {
            InventoryStocktakingVo vo = new InventoryStocktakingVo();
            BeanUtils.copyProperties(form, vo);
            voList.add(vo);
        });
        return voList;
    }

    @Override
    public InventoryStocktakingVo get(String formNo) {
        if (StringUtils.isEmpty(formNo)) {
            logger.error("formNo为空");
            return null;
        }
        InventoryStocktaking form = inventoryStocktakingRepository.findByFormNo(formNo);
        if (form == null) {
            logger.error("formNo[{}]无效", formNo);
            return null;
        }
        InventoryStocktakingVo formVo = new InventoryStocktakingVo();
        BeanUtils.copyProperties(form, formVo);
        List<InventoryStocktakingDetail> detailList = inventoryStocktakingDetailRepository.findByFormNoAndFinish(formNo, 0);
        if (detailList != null && !detailList.isEmpty()) {
            List<InventoryStocktakingDetailVo> voList = new ArrayList<>();
            detailList.forEach(detail -> {
                InventoryStocktakingDetailVo detailVo = new InventoryStocktakingDetailVo();
                BeanUtils.copyProperties(detail, detailVo);
                voList.add(detailVo);
            });
            formVo.setDetailVoList(voList);
        }
        return formVo;
    }

    @Override
    @Transactional
    public InventoryStocktaking Create(InventoryStocktaking inventoryStocktaking) {
        String formNo = InventoryStocktaking.CODE_PREFIX + System.currentTimeMillis();
        inventoryStocktaking.setFormNo(formNo);
        inventoryStocktaking.setState(InventoryStocktaking.STATE_CREATED);
        List<Object[]> objList = storageBatchRepository.findBatchTakingInfo(inventoryStocktaking.getStorageCode());
        //批次数据
        List<StorageBatchTakingVo> batchTakingVos = EntityUtils.castEntity(objList, StorageBatchTakingVo.class, new StorageBatchTakingVo());
        List<StorageDetail> storageDetails = storageDetailRepository.findByStorageCode(inventoryStocktaking.getStorageCode());
        List<InventoryStocktakingDetail> takingDetails = GenerateTakingDetails(batchTakingVos, storageDetails, inventoryStocktaking);
        InventoryStocktaking saved = inventoryStocktakingRepository.save(inventoryStocktaking);
        List<InventoryStocktakingDetail> savedList = inventoryStocktakingDetailRepository.saveAll(takingDetails);
        saved.setDetailList(savedList);
        return saved;
    }

    @Override
    @Transactional
    public InventoryStocktaking Save(InventoryStocktaking inventoryStocktaking) {
        List<InventoryStocktakingDetail> detailList = inventoryStocktaking.getDetailList();
        Set<Integer> productIds = new HashSet<>();
        for (InventoryStocktakingDetail detail : detailList) {
            productIds.add(detail.getMaterialId());
        }
        List<StorageBatch> storageBatchList = storageBatchRepository.findByMaterialIdInAndFinished(productIds, 0);
        Map<String, StorageBatch> storageBatchMap = new HashMap<>(storageBatchList.size());
        for (StorageBatch batch : storageBatchList) {
            String key = batch.getMaterialId() + batch.getBatchNo() + batch.getCountUnit();
            storageBatchMap.put(key, batch);
        }
        List<StorageDetail> storageDetails = storageDetailRepository.findByProductIdIn(productIds);
        Map<String, StorageDetail> storageDetailMap = new HashMap<>(storageDetails.size());
        for (StorageDetail storageDetail : storageDetails) {
            String key = storageDetail.getProductId() + storageDetail.getUnit();
            storageDetailMap.put(key, storageDetail);
        }
        List<StorageBatch> newBatchList = new ArrayList<>();
        String batchNo = DateUtil.timeShortFormat(new Date());
        for (InventoryStocktakingDetail detail : detailList) {
            String storageDetailKey = detail.getMaterialId() + detail.getStorageCountUnit();
            String batchKey = detail.getMaterialId() + detail.getBatchNo() + detail.getStorageCountUnit();
            //没有批次号 则创建新批次
            if (StringUtils.isEmpty(detail.getBatchNo())) {
                if (detail.getTackingCount() > 0) {
                    StorageBatch batch = new StorageBatch();
                    batch.setMaterialId(detail.getMaterialId());
                    batch.setName(detail.getName());
                    batch.setSpecification(detail.getSpecification());
                    batch.setBatchNo(batchNo);
                    batch.setCount(detail.getTackingCount());
                    batch.setCountUnit(detail.getStorageCountUnit());
                    batch.setStorageInCode(inventoryStocktaking.getFormNo());
                    batch.setBatchType("STOCKTAKING");
                    batch.setFinished(0);
                    newBatchList.add(batch);
                }
                StorageDetail storageDetail = storageDetailMap.get(storageDetailKey);
                if (storageDetail == null) {
                    logger.error("盘点error：未能找到库存信息，productId=[{}],unit=[{}]", detail.getMaterialId(), detail.getStorageCountUnit());
                    continue;
                }
                storageDetail.setCount(storageDetail.getCount() + (detail.getTackingCount() - detail.getStorageCount()));
            } else {
                StorageDetail storageDetail = storageDetailMap.get(storageDetailKey);
                StorageBatch storageBatch = storageBatchMap.get(batchKey);
                if (storageBatch == null || storageDetail == null) {
                    logger.error("盘点error：未能找到库存信息，productId=[{}],batchNo=[{}],unit=[{}]", detail.getMaterialId(), detail.getBatchNo(), detail.getStorageCountUnit());
                    continue;
                }
                storageDetail.setCount(storageDetail.getCount() + (detail.getTackingCount() - detail.getStorageCount()));
                storageBatch.setCount(storageBatch.getCount() + (detail.getTackingCount() - detail.getStorageCount()));
            }
            detail.setFinish(1);
        }
        storageDetailRepository.saveAll(storageDetailMap.values());
        storageBatchRepository.saveAll(storageBatchMap.values());
        if (newBatchList.size() > 0) {
            storageBatchRepository.saveAll(newBatchList);
        }
        inventoryStocktakingDetailRepository.saveAll(detailList);
        inventoryStocktakingRepository.save(inventoryStocktaking);
        return inventoryStocktaking;
    }

    @Override
    public InventoryStocktaking FindUncompletedOne() {
        InventoryStocktaking existOne = inventoryStocktakingRepository.findByStateOrState(InventoryStocktaking.STATE_CREATED, InventoryStocktaking.STATE_PROCESSING);
        if (existOne != null) {
            List<InventoryStocktakingDetail> details = inventoryStocktakingDetailRepository.findByFormNoAndFinish(existOne.getFormNo(), 0);
            existOne.setDetailList(details);
        }
        return existOne;
    }

    private List<InventoryStocktakingDetail> GenerateTakingDetails(List<StorageBatchTakingVo> batchTakingVos, List<StorageDetail> storageDetails, InventoryStocktaking inventoryStocktaking) {
        List<InventoryStocktakingDetail> list = new ArrayList<>();
        Map<String, List<StorageBatchTakingVo>> takingVoMap = new HashMap<>();
        for (StorageBatchTakingVo vo : batchTakingVos) {
            String key = vo.getMaterialId() + vo.getCountUnit();
            List<StorageBatchTakingVo> existList = takingVoMap.get(key);
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(vo);
                takingVoMap.put(key, existList);
            } else {
                existList.add(vo);
            }
        }
        for (StorageDetail storageDetail : storageDetails) {
            InventoryStocktakingDetail takingDetail = CreateInventoryStocktakingDetailByStorageDetail(storageDetail, inventoryStocktaking.getFormNo());
            String key = storageDetail.getProductId() + storageDetail.getUnit();
            List<StorageBatchTakingVo> batchList = takingVoMap.get(key);
            if (batchList == null) {
                takingDetail.setStorageCount(storageDetail.getCount());
                list.add(takingDetail);
                continue;
            } else {
                for (StorageBatchTakingVo vo : batchList) {
                    InventoryStocktakingDetail bacthDetail = new InventoryStocktakingDetail();
                    BeanUtils.copyProperties(takingDetail, bacthDetail);
                    bacthDetail.setStorageCount(vo.getCount());
                    bacthDetail.setBatchNo(vo.getBatchNo());
                    list.add(bacthDetail);
                }
            }
        }
        return list;
    }

    private InventoryStocktakingDetail CreateInventoryStocktakingDetailByStorageDetail(StorageDetail storageDetail, String formNo) {
        InventoryStocktakingDetail takingDetail = new InventoryStocktakingDetail();
        takingDetail.setFormNo(formNo);
        takingDetail.setMaterialNo(storageDetail.getMaterialNo());
        takingDetail.setFinish(0);
        takingDetail.setMaterialId(storageDetail.getProductId());
        takingDetail.setName(storageDetail.getName());
        takingDetail.setSpecification(storageDetail.getSpecification());
        takingDetail.setStorageCountUnit(storageDetail.getUnit());
        takingDetail.setTackingCount(0);
        return takingDetail;
    }

}
