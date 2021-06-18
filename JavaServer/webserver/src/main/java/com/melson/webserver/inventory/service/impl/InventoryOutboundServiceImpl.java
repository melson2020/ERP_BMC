package com.melson.webserver.inventory.service.impl;

import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IStorageBatchRepository;
import com.melson.webserver.dict.entity.StorageBatch;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IStorageBatch;
import com.melson.webserver.dict.service.IStorageDetail;
import com.melson.webserver.inventory.dao.*;
import com.melson.webserver.inventory.dto.OutboundRequireDto;
import com.melson.webserver.inventory.entity.InventoryOutbound;
import com.melson.webserver.inventory.entity.InventoryOutboundDetail;
import com.melson.webserver.inventory.entity.StorageUnit;
import com.melson.webserver.inventory.service.IInventoryOutboundService;
import com.melson.webserver.inventory.vo.InventoryOutboundDetailVo;
import com.melson.webserver.inventory.vo.InventoryOutboundVo;
import com.melson.webserver.order.entity.OrderDelivery;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.PickingTicket;
import com.melson.webserver.order.entity.PickingTicketDetail;
import com.melson.webserver.order.service.IOrderDeliveryService;
import com.melson.webserver.order.service.IPickingTicketService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 出库接口实现类
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@Service
public class InventoryOutboundServiceImpl implements IInventoryOutboundService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryOutboundServiceImpl.class);

    @Autowired
    private IInventoryOutboundRepository inventoryOutboundRepository;
    @Autowired
    private IInventoryOutboundDetailRepository inventoryOutboundDetailRepository;
    @Autowired
    private IPickingTicketService pickingTicketService;
    @Autowired
    private IStorageBatchRepository storageBatchRepository;
    @Autowired
    private IStorageDetail storageDetailService;
    @Autowired
    private IStorageUnitRepository storageUnitRepository;
    @Autowired
    private IStorageBatch storageBatchService;
    @Autowired
    private IOrderDeliveryService orderDeliveryService;

    @Override
    public List<InventoryOutboundVo> list(Date date) {
        List<InventoryOutbound> list = inventoryOutboundRepository.findByCreateDateAfter(date);
        if (list == null || list.isEmpty()) {
            logger.error("出库单为空");
            return null;
        }
        List<InventoryOutboundVo> voList = new ArrayList<>();
        list.forEach(form -> {
            InventoryOutboundVo vo = new InventoryOutboundVo();
            BeanUtils.copyProperties(form, vo);
            voList.add(vo);
        });
        return voList;
    }

    @Override
    public InventoryOutboundVo get(String formNo) {
        if (StringUtils.isEmpty(formNo)) {
            logger.error("formNo为空");
            return null;
        }
        InventoryOutbound form = inventoryOutboundRepository.findByFormNo(formNo);
        if (form == null) {
            logger.error("formNo[{}]无效", formNo);
            return null;
        }
        InventoryOutboundVo formVo = new InventoryOutboundVo();
        BeanUtils.copyProperties(form, formVo);
        List<InventoryOutboundDetail> detailList = inventoryOutboundDetailRepository.findByFormNo(formNo);
        if (detailList != null && !detailList.isEmpty()) {
            List<InventoryOutboundDetailVo> voList = new ArrayList<>();
            detailList.forEach(detail -> {
                InventoryOutboundDetailVo detailVo = new InventoryOutboundDetailVo();
                BeanUtils.copyProperties(detail, detailVo);
                voList.add(detailVo);
            });
            formVo.setDetailVoList(voList);
        }
        return formVo;
    }

    @Override
    @Transactional
    public InventoryOutbound save(InventoryOutboundVo vo, Integer userId) throws RuntimeException {
        // 1.保存出库单
        InventoryOutbound form = new InventoryOutbound();
        BeanUtils.copyProperties(vo, form);
        if (StringUtils.isEmpty(form.getFormNo())) {
            form.setFormNo(InventoryOutbound.CODE_PREFIX + System.currentTimeMillis());
        }
        inventoryOutboundRepository.saveAndFlush(form);
        // 2.保存出库明细
        if (vo.getDetailVoList() == null || vo.getDetailVoList().isEmpty()) {
            logger.error("出库明细为空");
            return form;
        }
        List<InventoryOutboundDetail> detailList = new ArrayList<>();
        vo.getDetailVoList().forEach(detailVo -> {
            InventoryOutboundDetail detail = new InventoryOutboundDetail();
            BeanUtils.copyProperties(detailVo, detail);
            detail.setFormNo(form.getFormNo());
            detailList.add(detail);
        });
        List<InventoryOutboundDetail> savedOutDetails = inventoryOutboundDetailRepository.saveAll(detailList);
        // 3.修改库存
        storageDetailService.InventoryOut(form, savedOutDetails);
        return form;
    }

    @Override
    public InventoryOutboundVo GenerateOutBoundWithExistTicket(Integer ticketId, String ticketType, Integer userId) throws RuntimeException {
        switch (ticketType) {
            case InventoryOutbound.TYPE_PICKING:
                PickingTicket pickingTicket = pickingTicketService.FindById(ticketId);
                List<OutboundRequireDto> outRequires = pickingTicketService.GenerateOutRequireList(ticketId);
                return GenerateInventoryOutBound(outRequires, pickingTicket.getTicketNo(), userId);
            case InventoryOutbound.TYPE_DELIVERY:
                OrderDelivery orderDelivery = orderDeliveryService.FindById(ticketId);
                List<OutboundRequireDto> orderOutRequires = orderDeliveryService.GenerateOutRequires(ticketId);
                return GenerateInventoryOutBound(orderOutRequires, orderDelivery.getTicketNo(), userId);
        }
        return null;
    }

    /**
     * 不考虑客户给料等入库信息 直接根据先进先出原则出库 客户给料只做状态提示 库存充足 也能领料
     *
     * @param outRequireList 出库需求
     * @return
     * @throws RuntimeException 运行错误 由业务定义
     */
    private InventoryOutboundVo GenerateInventoryOutBound(List<OutboundRequireDto> outRequireList, String sourceNo, Integer userId) throws RuntimeException {
        if (outRequireList == null || outRequireList.size() <= 0) return null;
        Set<Integer> productIds = new HashSet<>();
        outRequireList.forEach(outboundRequireDto -> {
            productIds.add(outboundRequireDto.getMaterialId());
        });
        //获取当前转换单位备用
        List<StorageUnit> units = storageUnitRepository.findByProductIdIn(productIds);
        //按照productId 分组
        Map<Integer, List<StorageUnit>> storageUnitMap = new HashMap<>();
        for (StorageUnit unit : units) {
            List<StorageUnit> existList = storageUnitMap.get(unit.getProductId());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(unit);
                storageUnitMap.put(unit.getProductId(), existList);
            } else {
                existList.add(unit);
            }
        }
        //获取库存备用
        List<StorageDetail> storageDetailList = storageDetailService.FindByProductIds(productIds);
        //按照productId 分组
        Map<Integer, List<StorageDetail>> storageDetailMap = new HashMap<>();
        for (StorageDetail storageDetail : storageDetailList) {
            List<StorageDetail> existList = storageDetailMap.get(storageDetail.getProductId());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(storageDetail);
                storageDetailMap.put(storageDetail.getProductId(), existList);
            } else {
                existList.add(storageDetail);
            }
        }
        //获取库存批次备用
        List<StorageBatch> storageBatchList = storageBatchService.FindByProductIds(productIds, 0);
        //按照productId 分组
        Map<Integer, List<StorageBatch>> storageBatchMap = new HashMap<>();
        for (StorageBatch storageBatch : storageBatchList) {
            List<StorageBatch> existList = storageBatchMap.get(storageBatch.getMaterialId());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(storageBatch);
                storageBatchMap.put(storageBatch.getMaterialId(), existList);
            } else {
                existList.add(storageBatch);
            }
        }
        List<InventoryOutboundDetailVo> detailVos = new ArrayList<>();
        InventoryOutboundVo inventoryOutbound = new InventoryOutboundVo();
        inventoryOutbound.setFormNo(InventoryOutbound.CODE_PREFIX + System.currentTimeMillis());
        inventoryOutbound.setType(InventoryOutbound.TYPE_PICKING);
        inventoryOutbound.setSourceNo(sourceNo);
        inventoryOutbound.setCreateDate(new Date());
        inventoryOutbound.setCreateUser(userId);
        List<InventoryOutboundDetailVo> voList = GenerateOutListWithPickingList(outRequireList, storageDetailMap, storageUnitMap, storageBatchMap);
        detailVos.addAll(voList);
        inventoryOutbound.setDetailVoList(detailVos);
        return inventoryOutbound;
    }


    //依据出库详细 生产出库信息
    private List<InventoryOutboundDetailVo> GenerateOutListWithPickingList(List<OutboundRequireDto> outRequireList, Map<Integer, List<StorageDetail>> storageDetailMap, Map<Integer, List<StorageUnit>> storageUnitMap, Map<Integer, List<StorageBatch>> storageBatchMap) throws RuntimeException {
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        for (OutboundRequireDto outDetail : outRequireList) {
            //计算出库存总量 并且把批次数量转化为 基础单位数量
            Integer pTotalCount = ComputedTotalStorage(storageDetailMap.get(outDetail.getMaterialId()), outDetail.getUnit(), storageUnitMap.get(outDetail.getMaterialId()));
            //按照先进先出原则 获取出库批次
            if (pTotalCount >= outDetail.getCount()) {
                //总库存是否充足  打上库存充足标记
                List<InventoryOutboundDetailVo> matchBatchOunBoundList = GenerateOutBoundListWithStorageBatch(storageBatchMap.get(outDetail.getMaterialId()), outDetail, storageUnitMap.get(outDetail.getMaterialId()));
                //不存在批次，从库存中直接出货
                if (matchBatchOunBoundList == null || matchBatchOunBoundList.size() <= 0) {
                    //todo:批次数据不存在 则数据不准 抛出错误
//                    throw  new RuntimeException("未找到批次信息，请确认数据是否准确");
                    List<InventoryOutboundDetailVo> outVos = GenerateOutBoundListWithStorageDetail(storageDetailMap.get(outDetail.getMaterialId()), outDetail.getCount(), outDetail.getUnit(), outDetail, storageUnitMap.get(outDetail.getMaterialId()));
                    SetStorageDesAndStorageCode(storageDetailMap.get(outDetail.getMaterialId()), outVos);
                    voList.addAll(outVos);
                } else {
                    //存在批次 在批次中出货
                    SetStorageDesAndStorageCode(storageDetailMap.get(outDetail.getMaterialId()), matchBatchOunBoundList);
                    voList.addAll(matchBatchOunBoundList);
                }
            } else {
                //打上采购标记
                InventoryOutboundDetailVo vo = new InventoryOutboundDetailVo();
                vo.setMaterialNo(outDetail.getMaterialNo());
                vo.setName(outDetail.getName());
                vo.setSpecification(outDetail.getSpecification());
                vo.setCount(outDetail.getCount());
                vo.setUnit(outDetail.getUnit());
                vo.setMaterialId(outDetail.getMaterialId());
                vo.setStorageCount(pTotalCount);
                //库存不足 采购
                vo.setStorageFlag(0);
                SetStorageDesAndStorageCode(storageDetailMap.get(outDetail.getMaterialId()), vo);
                voList.add(vo);
            }
        }
        return voList;
    }

    //按照先进先出原则 获取出库批次
    private List<InventoryOutboundDetailVo> GenerateOutBoundListWithStorageBatch(List<StorageBatch> existList, OutboundRequireDto outDetail, List<StorageUnit> unitList) {
        if (existList == null) return null;
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        //按照批次排序 从小到大
        existList.sort(new Comparator<StorageBatch>() {
            @Override
            public int compare(StorageBatch o1, StorageBatch o2) {
                long b1 = Long.parseLong(o1.getBatchNo());
                long b2 = Long.parseLong(o2.getBatchNo());
                return b1 > b2 ? -1 : 1;
            }
        });
        Map<String, StorageUnit> packageUnitMap = new HashMap<>();
        if (unitList != null) {
            unitList.forEach(unit -> {
                packageUnitMap.put(unit.getPackageUnit(), unit);
            });
        }
        Integer count = 0;
        for (StorageBatch storageBatch : existList) {
            InventoryOutboundDetailVo vo = GenerateWithBaseInfoPickDetail(outDetail);
            vo.setOutCountUnit(storageBatch.getCountUnit());
            vo.setStorageFlag(1);
            vo.setBatchNo(storageBatch.getBatchNo());
            //是否单位相同
            Boolean sameUnit = storageBatch.getCountUnit().equals(outDetail.getUnit());
            Integer needCount = outDetail.getCount() - count;
            if (sameUnit) {
                count += storageBatch.getCount();
            } else {
                StorageUnit packageUnit = packageUnitMap.get(storageBatch.getCountUnit());
                if (packageUnit == null) {
                    logger.error("查找批次error: 未找到换算单位[{}]", storageBatch.getCountUnit());
                    continue;
                }
                count += (storageBatch.getCount() * packageUnit.getBaseUnitConvertCount());
            }
            if (count < outDetail.getCount()) {
                //数量未达标 全部出完
                vo.setOutCount(storageBatch.getCount());
            } else if (count == outDetail.getCount()) {
                //数量正好不要拆包
                vo.setOutCount(storageBatch.getCount());
                voList.add(vo);
                return voList;
            } else {
                if (sameUnit) {
                    //相同单位不用拆包
                    vo.setOutCount(needCount);
                } else {
                    //拆包
                    StorageUnit packageUnit = packageUnitMap.get(storageBatch.getCountUnit());
                    //出库数量
                    Double outUnitCount = Math.ceil(new Double(needCount) / packageUnit.getBaseUnitConvertCount());
                    //拆包去走数量 如拆一包取走10支
                    Integer pickCount = needCount % packageUnit.getBaseUnitConvertCount();
                    vo.setOutCount(outUnitCount.intValue());
                    vo.setUnPackageCount(1);
                    vo.setUnPackageCountUnit(storageBatch.getCountUnit());
                    vo.setUnPackagePickCount(pickCount);
                }
                voList.add(vo);
                return voList;
            }
        }
        return voList;
    }

    private void SetStorageDesAndStorageCode(List<StorageDetail> storageDetails, List<InventoryOutboundDetailVo> voList) {
        if (storageDetails == null || storageDetails.size() <= 0) return;
        String des = "";
        storageDetails.sort(new Comparator<StorageDetail>() {
            @Override
            public int compare(StorageDetail o1, StorageDetail o2) {
                return o1.getLevel() > o2.getLevel() ? -1 : 1;
            }
        });
        for (StorageDetail storageDetail : storageDetails) {
            des = des + (storageDetail.getCount() + storageDetail.getUnit());
        }
        String code = storageDetails.get(0).getStorageCode();
        for (InventoryOutboundDetailVo inventoryOutboundDetailVo : voList) {
            inventoryOutboundDetailVo.setStorageDes(des);
            inventoryOutboundDetailVo.setStorageCode(code);
        }
        ;
    }

    private void SetStorageDesAndStorageCode(List<StorageDetail> storageDetails, InventoryOutboundDetailVo vo) {
        if (storageDetails == null || storageDetails.size() <= 0) return;
        String des = "";
        storageDetails.sort(new Comparator<StorageDetail>() {
            @Override
            public int compare(StorageDetail o1, StorageDetail o2) {
                return o1.getLevel() > o2.getLevel() ? -1 : 1;
            }
        });
        for (StorageDetail storageDetail : storageDetails) {
            des = des + (storageDetail.getCount() + storageDetail.getUnit());
        }
        String code = storageDetails.get(0).getStorageCode();
        vo.setStorageDes(des);
        vo.setStorageCode(code);
    }

    private InventoryOutboundDetailVo GenerateWithBaseInfoPickDetail(OutboundRequireDto outDetail) {
        InventoryOutboundDetailVo vo = new InventoryOutboundDetailVo();
        vo.setMaterialNo(outDetail.getMaterialNo());
        vo.setName(outDetail.getName());
        vo.setSpecification(outDetail.getSpecification());
        vo.setCount(outDetail.getCount());
        vo.setUnit(outDetail.getUnit());
        vo.setMaterialId(outDetail.getMaterialId());
        return vo;
    }

    private List<InventoryOutboundDetailVo> GenerateOutBoundListWithStorageDetail(List<StorageDetail> storageDetails, Integer outCount, String baseUnit, OutboundRequireDto outDetail, List<StorageUnit> unitList) {
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        if (unitList == null) {
            //没有其他包装单位 并且库存充足
            InventoryOutboundDetailVo vo = GenerateWithBaseInfoPickDetail(outDetail);
            StorageDetail storageDetail = storageDetails.get(0);
            vo.setOutCount(outCount);
            vo.setOutCountUnit(storageDetail.getUnit());
            vo.setStorageCode(storageDetail.getStorageCode());
            vo.setStorageFlag(1);
            vo.setStorageDes(storageDetail.getCount() + storageDetail.getUnit());
            voList.add(vo);
            return voList;
        } else {
            //有多种包装单位 先按对库存单位优先级排序
            storageDetails.sort(new Comparator<StorageDetail>() {
                @Override
                public int compare(StorageDetail o1, StorageDetail o2) {
                    return o1.getLevel() < o2.getLevel() ? -1 : 1;
                }
            });
            Map<String, StorageUnit> packageUnitMap = new HashMap<>();
            unitList.forEach(unit -> {
                packageUnitMap.put(unit.getPackageUnit(), unit);
            });
            Integer count = 0;
            for (StorageDetail storageDetail : storageDetails) {
                InventoryOutboundDetailVo vo = GenerateWithBaseInfoPickDetail(outDetail);
                vo.setOutCountUnit(storageDetail.getUnit());
                vo.setStorageCode(storageDetail.getStorageCode());
                vo.setStorageFlag(1);
                //剩余需求数量
                Integer needCount = outCount - count;
                //是否单位相同
                Boolean sameUnit = storageDetail.getUnit().equals(baseUnit);
                if (sameUnit) {
                    count += storageDetail.getCount();
                } else {
                    StorageUnit packageUnit = packageUnitMap.get(storageDetail.getUnit());
                    if (packageUnit == null) {
                        logger.error("生成出库详细error: 未找到换算单位[{}]", storageDetail.getUnit());
                        continue;
                    }
                    count += (storageDetail.getCount() * packageUnit.getBaseUnitConvertCount());
                }
                //数量刚刚超出满足出库数量
                if (count > outCount) {
                    if (sameUnit) {
                        //包装单位一致  不需要拆包 并且当前库存满足需求
                        vo.setOutCount(needCount);
                    } else {
                        //需要拆包
                        StorageUnit packageUnit = packageUnitMap.get(storageDetail.getUnit());
                        //出库数量
                        Double outUnitCount = Math.ceil(new Double(needCount) / packageUnit.getBaseUnitConvertCount());
                        //拆包去走数量 如拆一包取走10支
                        Integer pickCount = needCount % packageUnit.getBaseUnitConvertCount();
                        vo.setOutCount(outUnitCount.intValue());
                        vo.setUnPackageCount(1);
                        vo.setUnPackageCountUnit(storageDetail.getUnit());
                        vo.setUnPackagePickCount(pickCount);
                    }
                    voList.add(vo);
                    return voList;
                } else {
                    //数量不足 则全部出完当前库存
                    vo.setOutCount(storageDetail.getCount());
                    voList.add(vo);
                }
                //
                if (count == outCount) {
                    voList.add(vo);
                    return voList;
                }
            }
        }
        return voList;
    }

    //计算库存总数
    private Integer ComputedTotalStorage(List<StorageDetail> storageDetails, String baseUnit, List<StorageUnit> unitList) {
        Integer totalStorage = 0;
        //无库存
        if (storageDetails == null) return totalStorage;
        Map<String, StorageUnit> packageUnitMap = new HashMap<>();
        if (unitList != null) {
            unitList.forEach(unit -> {
                packageUnitMap.put(unit.getPackageUnit(), unit);
            });
        }
        for (StorageDetail storageDetail : storageDetails) {
            if (storageDetail.getUnit().equals(baseUnit)) {
                totalStorage += storageDetail.getCount();
            } else {
                StorageUnit packageUnit = packageUnitMap.get(storageDetail.getUnit());
                if (packageUnit == null) {
                    logger.error("计算总数error: 未找到换算单位[{}]", storageDetail.getUnit());
                    continue;
                }
                totalStorage += (storageDetail.getCount() * packageUnit.getBaseUnitConvertCount());
            }
        }
        return totalStorage;
    }


    @Override
    public List<InventoryOutboundDetailVo> GenerateDetailVoBatchInfo(InventoryOutboundDetailVo vo) {
        List<StorageBatch> storageBatchList = storageBatchRepository.findByMaterialIdAndFinishedAndCountUnitOrderByBatchNo(vo.getMaterialId(), 0, vo.getOutCountUnit());
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        Integer count = 0;
        for (StorageBatch storageBatch : storageBatchList) {
            count += storageBatch.getCount();
            InventoryOutboundDetailVo detailVo = new InventoryOutboundDetailVo();
            BeanUtils.copyProperties(vo, detailVo);
            detailVo.setBatchNo(storageBatch.getBatchNo());
            if (count > vo.getOutCount()) {
                detailVo.setOutCount(vo.getOutCount() - count + storageBatch.getCount());
                voList.add(detailVo);
                break;
            } else if (count == vo.getOutCount()) {
                detailVo.setOutCount(storageBatch.getCount());
                voList.add(detailVo);
                break;
            } else {
                detailVo.setOutCount(storageBatch.getCount());
                voList.add(detailVo);
            }
        }
        return voList;
    }

}
