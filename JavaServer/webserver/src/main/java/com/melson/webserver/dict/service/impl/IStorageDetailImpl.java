package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IStorageBatchRepository;
import com.melson.webserver.dict.dao.IStorageDetailRepository;
import com.melson.webserver.dict.entity.StorageBatch;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IStorageDetail;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.entity.InventoryInboundDetail;
import com.melson.webserver.inventory.entity.StorageUnit;
import com.melson.webserver.inventory.resource.InventoryStocktakingResource;
import com.melson.webserver.inventory.vo.StorageBatchVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Messi on 2021/5/10
 */
@Service
public class IStorageDetailImpl extends AbstractService<StorageDetail> implements IStorageDetail {
    private static final Logger logger = LoggerFactory.getLogger(IStorageDetailImpl.class);
    private final IStorageDetailRepository storageDetailRepository;
    private final IStorageBatchRepository storageBatchRepository;

    public IStorageDetailImpl(IStorageDetailRepository storageDetailRepository, IStorageBatchRepository storageBatchRepository) {
        this.storageDetailRepository = storageDetailRepository;
        this.storageBatchRepository = storageBatchRepository;
    }

    @Override
    public JpaRepository<StorageDetail, String> getRepository() {
        return storageDetailRepository;
    }

    @Override
    public StorageDetail findByProductMaterialNo(String productNo) {
        return storageDetailRepository.findByMaterialNo(productNo);
    }

    @Override
    @Transactional
    public Integer DeleteStorage(String materialNo) {
        return storageDetailRepository.deleteByMaterialNo(materialNo);
    }

    @Override
    public Page<StorageDetail> FindAllStorageList(String searchValue, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "productId");
        Specification<StorageDetail> specification = new Specification<StorageDetail>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<StorageDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (!StringUtils.isEmpty(searchValue)) {
                    predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + searchValue + "%"), criteriaBuilder.like(root.get("materialNo"), "%" + searchValue + "%")));
                }
                return predicate;
            }
        };
        Page<StorageDetail> pageList = storageDetailRepository.findAll(specification, pageable);
        return pageList;
    }

    @Override
    public StorageDetail SaveOneWhileNotExist(StorageUnit unit) {
        StorageDetail existOne = storageDetailRepository.findByProductIdAndUnit(unit.getProductId(), unit.getPackageUnit());
        if (existOne == null) {
            StorageDetail detail = storageDetailRepository.findByProductIdAndUnit(unit.getProductId(), unit.getConvertUnit());
            if (detail == null) {
                logger.error("新增包装单位详细error: stroage_detail 未查询到 productId=[{}]和 unit [{}] ", unit.getProductId(), unit.getConvertUnit());
                return null;
            }
            StorageDetail storageDetail = new StorageDetail();
            storageDetail.setMaterialNo(detail.getMaterialNo());
            storageDetail.setName(detail.getName());
            storageDetail.setSpecification(detail.getSpecification());
            storageDetail.setCount(0);
            storageDetail.setSupplyId(detail.getSupplyId());
            if (detail.getLastestPrice() != null) {
                storageDetail.setLastestPrice(detail.getLastestPrice().multiply(new BigDecimal(unit.getConvertCount())));
            }
            storageDetail.setUnit(unit.getPackageUnit());
            storageDetail.setFeature(detail.getFeature());
            storageDetail.setStorageCode(detail.getStorageCode());
            storageDetail.setProductId(detail.getProductId());
            storageDetail.setLevel(unit.getLevel());
            return storageDetailRepository.save(storageDetail);
        } else {
            return null;
        }
    }

    @Override
    public StorageDetail FindByProductIdAndUnit(Integer productId, String unit) {
        return storageDetailRepository.findByProductIdAndUnit(productId, unit);
    }

    @Override
    public void DeleteOne(StorageDetail storageDetail) {
         storageDetailRepository.delete(storageDetail);
    }

    @Override
    public List<StorageDetail> InventoryIn(InventoryInbound inbound, List<InventoryInboundDetail> details) throws RuntimeException{
        Set<Integer> productIdSet=new HashSet<>();
        details.forEach(inventoryInboundDetail -> {
            if(!productIdSet.contains(inventoryInboundDetail.getMaterialId())){
                productIdSet.add(inventoryInboundDetail.getMaterialId());
            }
        });
        List<StorageDetail> storageDetails=storageDetailRepository.findByProductIdIn(productIdSet);
        Map<String,StorageDetail> storageDetailMap=new HashMap<>(storageDetails.size());
        for(StorageDetail storageDetail:storageDetails){
            String key=storageDetail.getProductId()+storageDetail.getUnit();
            storageDetailMap.put(key,storageDetail);
        }
        List<StorageDetail> storageUpdateList=new ArrayList<>();
        List<StorageBatch> storageBatchList=new ArrayList<>();
        for(InventoryInboundDetail inboundDetail:details){
            String key=inboundDetail.getMaterialId()+inboundDetail.getUnit();
            StorageDetail storage=storageDetailMap.get(key);
            if(storage==null){
                String log="未找到对应的库存详细:"+inboundDetail.getMaterialName()+"，单位："+inboundDetail.getUnit();
                logger.error("入库error: 未找到对应的库存详细：productId=[{}]和 unit [{}]",inboundDetail.getMaterialId(),inboundDetail.getUnit());
                throw new RuntimeException(log);
            }
            storage.setCount(inboundDetail.getCount().add(new BigDecimal(storage.getCount())).intValue());
            storageUpdateList.add(storage);
            storageBatchList.add(CreateBatch(storage,inbound,inboundDetail));
        }

        //插入新增批次
        storageBatchRepository.saveAll(storageBatchList);
        //更新库存列表
        return storageDetailRepository.saveAll(storageUpdateList);
    }


    @Override
    public List<StorageBatchVo> FindStorageBatchInfo(Set<String> materialNos) {
        List<Object[]> objects=storageDetailRepository.findStorageDetailBatchInfo(materialNos);
        List<StorageBatchVo> storageBatchVos= EntityUtils.castEntity(objects,StorageBatchVo.class,new StorageBatchVo());
        return storageBatchVos;
    }

    @Override
    public List<StorageDetail> FindByProductIds(Set<Integer> productIds) {
        return storageDetailRepository.findByProductIdIn(productIds);
    }

    @Override
    public List<StorageDetail> FindByProductId(Integer productId) {
        return storageDetailRepository.findByProductId(productId);
    }

    private StorageBatch CreateBatch(StorageDetail storageDetail,InventoryInbound inbound,InventoryInboundDetail inboundDetail){
        StorageBatch storageBatch=new StorageBatch();
        storageBatch.setMaterialId(storageDetail.getProductId());
        storageBatch.setName(storageDetail.getName());
        storageBatch.setSpecification(storageDetail.getSpecification());
        storageBatch.setBatchNo(inbound.getBatchNo());
        storageBatch.setSupplyId(storageDetail.getSupplyId());
        storageBatch.setCount(inboundDetail.getCount().intValue());
        storageBatch.setCountUnit(inboundDetail.getUnit());
        storageBatch.setBatchType(inbound.getType());
        storageBatch.setStorageInCode(inbound.getFormNo());
        storageBatch.setFinished(0);
       return storageBatch;
    }
}
