package com.melson.webserver.order.service.impl;

import com.melson.base.utils.DateUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IStorageDetailRepository;
import com.melson.webserver.dict.entity.StorageBatch;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.service.IStorageBatch;
import com.melson.webserver.dict.service.IStorageDetail;
import com.melson.webserver.dict.vo.BomProcessVo;
import com.melson.webserver.dict.vo.BomVo;
import com.melson.webserver.inventory.dao.IInventoryInboundDetailRepository;
import com.melson.webserver.inventory.dao.IInventoryInboundRepository;
import com.melson.webserver.inventory.dao.IStorageUnitRepository;
import com.melson.webserver.inventory.dto.OutboundRequireDto;
import com.melson.webserver.inventory.entity.*;
import com.melson.webserver.inventory.vo.*;
import com.melson.webserver.order.dao.IPickingTicketDetailRepository;
import com.melson.webserver.order.dao.IPickingTicketRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.resource.OrderFormResource;
import com.melson.webserver.order.service.IPickingTicketService;
import com.sun.xml.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Service
public class PickingTicketServiceImpl implements IPickingTicketService {
    private static final Logger logger = LoggerFactory.getLogger(PickingTicketServiceImpl.class);
    @Autowired
    private IPickingTicketRepository pickingTicketRepository;
    @Autowired
    private IBoms bomsService;
    @Autowired
    private IPickingTicketDetailRepository pickingTicketDetailRepository;
    @Autowired
    private IStorageDetail storageDetailService;
    @Autowired
    private IInventoryInboundDetailRepository iInventoryInboundDetailRepository;
    @Autowired
    private IInventoryInboundRepository iInventoryInboundRepository;
    @Autowired
    private IStorageUnitRepository storageUnitRepository;
    @Autowired
    private IStorageBatch storageBatchService;

    @Override
    public PickingTicket GeneratePickTicketWithPlanProcess(List<ProducePlanProcess> processList, ProducePlan plan, List<ProducePlanDetail> detailList) {
        if (processList == null || processList.size() <= 0) return null;
        PickingTicket ticket = new PickingTicket();
        ticket.setTicketNo("L" + System.currentTimeMillis());
        ticket.setSourceId(plan.getId());
        ticket.setSourceNo(plan.getPlanNo());
        ticket.setType(PickingTicket.TYPE_PLAN);
        ticket.setCreateDate(new Date());
        ticket.setState(PickingTicket.STATE_CREATE);
        ticket.setInboundTicketNo("");
        ticket.setOutboundTicketNo("");
        pickingTicketRepository.saveAndFlush(ticket);
        Map<Integer, ProducePlanDetail> detailIdMap = new HashMap<>(detailList.size());
        for (ProducePlanDetail detail : detailList) {
            detailIdMap.put(detail.getProductId(), detail);
        }
        List<PickingTicketDetail> ticketDetails = new ArrayList<>();
        List<ProducePlanProcess> rootList = GetProducePlanProcessRootList(processList);
        //??????????????????
        boolean customerMaterial = false;
        for (ProducePlanProcess p : rootList) {
            PickingTicketDetail detail = CreateTicketDetailWithProcess(p, detailIdMap.get(p.getProductId()), ticket.getId());
            if (detail.getType().equals(OrderFormDetail.PRODUCE_TYPE_D)) {
                customerMaterial = true;
            }
            ticketDetails.add(detail);
        }
        if (customerMaterial) {
            ticket.setCustomerMaterialFlag(PickingTicket.CUSTOMER_MATERIAL_FLAY_Y);
        } else {
            ticket.setCustomerMaterialFlag(PickingTicket.CUSTOMER_MATERIAL_FLAY_N);
        }
        pickingTicketRepository.saveAndFlush(ticket);
        pickingTicketDetailRepository.saveAll(ticketDetails);
        return ticket;
    }

    private List<ProducePlanProcess> GetProducePlanProcessRootList(List<ProducePlanProcess> list) {
        List<ProducePlanProcess> rootList = new ArrayList<>();
        Set<String> noSet = new HashSet<>();
        list.forEach(process -> {
            if (!noSet.contains(process.getPartMaterialNo())) {
                noSet.add(process.getPartMaterialNo());
            }
        });
        for (ProducePlanProcess vo : list) {
            if (noSet.contains(vo.getMaterialNo())) continue;
            rootList.add(vo);
        }
        return rootList;
    }

    @Override
    public PickingTicket GeneratePickTicketWithOrderFormDetail(OrderForm form, List<OrderFormDetail> details) {
        if (details == null || details.size() <= 0) return null;
        Set<String> bomNos = new HashSet<>();
        details.forEach(formDetail -> {
            if (formDetail.getBomNos().size() > 0) {
                for (String no : formDetail.getBomNos()) {
                    bomNos.add(no);
                }
            }
        });
        List<BomVo> bomVos = bomsService.findBomVoInBomNos(bomNos);
        Map<String, List<BomVo>> bomVoMap = new HashMap<>();
        for (BomVo vo : bomVos) {
            List<BomVo> existList = bomVoMap.get(vo.getBomNo());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(vo);
                bomVoMap.put(vo.getBomNo(), existList);
            } else {
                existList.add(vo);
            }
        }
        PickingTicket ticket = new PickingTicket();
        ticket.setTicketNo(PickingTicket.TICKET_NO_CHAR + System.currentTimeMillis());
        ticket.setSourceId(form.getId());
        ticket.setSourceNo(form.getFormNo());
        ticket.setType(PickingTicket.TYPE_ORDER);
        ticket.setCreateDate(new Date());
        ticket.setState(PickingTicket.STATE_CREATE);
        pickingTicketRepository.saveAndFlush(ticket);
        List<PickingTicketDetail> pickingTicketDetailList = new ArrayList<>();
        for (OrderFormDetail detail : details) {
            if (detail.getBomNos() == null || detail.getBomNos().size() <= 0) {
                logger.warn("orderFormDetail ????????????????????? ????????????bomNos, detail id" + detail.getId());
                continue;
            }
            List<BomVo> boms = new ArrayList<>();
            for (String bomNo : detail.getBomNos()) {
                boms.addAll(bomVoMap.get(bomNo));
            }
            List<BomVo> rootList = GetRootList(boms);
            for (BomVo vo : rootList) {
                pickingTicketDetailList.add(CreateTicketWithBomVo(vo, detail, ticket.getId()));
            }
        }
        pickingTicketDetailRepository.saveAll(pickingTicketDetailList);
        return ticket;
    }

    @Override
    public PickingTicket FindByPlanId(Integer planId) {
        return pickingTicketRepository.findBySourceIdAndType(planId, PickingTicket.TYPE_PLAN);
    }

    @Override
    public PickingTicket FindBySourceIdAndType(Integer sourceId, String type) {
        return pickingTicketRepository.findBySourceIdAndType(sourceId, type);
    }

    @Override
    public List<PickingTicket> FindByCustomerMaterialFlagAndState(String flag, String state) {
        return pickingTicketRepository.findByCustomerMaterialFlagAndState(flag, state);
    }

    @Override
    public List<PickingTicket> FindOutBoundPickingList() {
        return pickingTicketRepository.findByStateOrState(PickingTicket.STATE_CREATE, PickingTicket.STATE_INBOUND);
    }

    @Override
    public InventoryInboundVo GenerateInventoryInBound(Integer ticketId, Integer userId) {
        PickingTicket pickingTicket = pickingTicketRepository.findById(ticketId).orElse(null);
        List<Object[]> picketTicketDetailList = pickingTicketDetailRepository.findInBoundListByTicketId(pickingTicket.getId());
        List<InventoryInboundDetailVo> detailVos = EntityUtils.castEntity(picketTicketDetailList, InventoryInboundDetailVo.class, new InventoryInboundDetailVo());
        ;
        InventoryInboundVo inventoryInbound = new InventoryInboundVo();
        inventoryInbound.setFormNo(InventoryInbound.CODE_PREFIX + System.currentTimeMillis());
        inventoryInbound.setBatchNo(DateUtil.timeShortFormat(new Date()));
        inventoryInbound.setType(InventoryInbound.TYPE_OEM);
        inventoryInbound.setSourceNo(pickingTicket.getTicketNo());
        inventoryInbound.setSourceId(ticketId);
        inventoryInbound.setCreateDate(new Date());
        inventoryInbound.setCreateUser(userId);
        inventoryInbound.setDetailVoList(detailVos);
        return inventoryInbound;
    }

    @Override
    public int UpdateAfterInBound(Integer id, String inboundNo, String state) {
        return pickingTicketRepository.updateAfterInBound(id, inboundNo, state);
    }

    @Override
    public InventoryOutboundVo GenerateInventoryOutBound(Integer ticketId, Integer userId) throws RuntimeException {
        PickingTicket pickingTicket = pickingTicketRepository.findById(ticketId).orElse(null);
        // ???????????????????????????
        if (pickingTicket.getCustomerMaterialFlag().equals(PickingTicket.CUSTOMER_MATERIAL_FLAY_Y) && (pickingTicket.getInboundTicketNo() == null || StringUtils.isEmpty(pickingTicket.getInboundTicketNo()))) {
            new RuntimeException("????????????????????????????????????");
        }
        List<PickingTicketDetail> pickingTicketDetailList = pickingTicketDetailRepository.findByTicketId(pickingTicket.getId());
        Set<String> materialNos = new HashSet<>();
        pickingTicketDetailList.forEach(pickingTicketDetail -> {
            materialNos.add(pickingTicketDetail.getMaterialNo());
        });
        if ((materialNos.size() > 900)) {
            new RuntimeException("???????????????????????????????????????");
        }
//        List<StorageBatchVo> storageBatchVoList = storageDetailService.FindStorageBatchInfo(materialNos);
        //key??? productId+batchNo+unit
//        Map<String, StorageBatchVo> stringStorageBatchVoMap = new HashMap<>();
        //??????productId ?????? ?????????????????????
//        Map<Integer, List<StorageBatchVo>> productStorageMap = new HashMap<>();
//        storageBatchVoList.forEach(storageBatchVo -> {
//            stringStorageBatchVoMap.put(storageBatchVo.getMaterialId() + storageBatchVo.getBatchNo() + storageBatchVo.getMaterialUnit(), storageBatchVo);
//            List<StorageBatchVo> existList = productStorageMap.get(storageBatchVo.getMaterialId());
//            if (existList == null) {
//                existList = new ArrayList<>();
//                existList.add(storageBatchVo);
//                productStorageMap.put(storageBatchVo.getMaterialId(), existList);
//            } else {
//                existList.add(storageBatchVo);
//            }
//        });
        Set<Integer> productIds = new HashSet<>();
        pickingTicketDetailList.forEach(pickingTicketDetail -> {
            productIds.add(pickingTicketDetail.getProductId());
        });
        //??????????????????????????????
        List<StorageUnit> units = storageUnitRepository.findByProductIdIn(productIds);
        //??????productId ??????
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
        //??????????????????
        List<StorageDetail> storageDetailList = storageDetailService.FindByProductIds(productIds);
        //??????productId ??????
        Map<Integer, List<StorageDetail>> storageDetailMap = new HashMap<>();
        for (StorageDetail storageDetail : storageDetailList) {
            List<StorageDetail> existList =storageDetailMap.get(storageDetail.getProductId());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(storageDetail);
                storageDetailMap.put(storageDetail.getProductId(), existList);
            } else {
                existList.add(storageDetail);
            }
        }
        //????????????????????????
        List<StorageBatch> storageBatchList = storageBatchService.FindByProductIds(productIds, 0);
        //??????productId ??????
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
        inventoryOutbound.setSourceNo(pickingTicket.getTicketNo());
        inventoryOutbound.setCreateDate(new Date());
        inventoryOutbound.setCreateUser(userId);
        //???????????? ?????????????????????
        List<PickingTicketDetail> produceList = new ArrayList<>();
        List<PickingTicketDetail> oemList = new ArrayList<>();
        pickingTicketDetailList.forEach(pickingTicketDetail -> {
            if (!pickingTicketDetail.getType().equals(OrderFormDetail.PRODUCE_TYPE_D)) {
                produceList.add(pickingTicketDetail);
            }else {
                oemList.add(pickingTicketDetail);
            }
        });
        //???????????? ?????????????????? ??????????????????
        if (pickingTicket.getCustomerMaterialFlag().equals(PickingTicket.CUSTOMER_MATERIAL_FLAY_Y)) {
            detailVos.addAll(GenerateOutListWithInBoundList(pickingTicket.getInboundTicketNo(), oemList,storageDetailMap));
        }
        List<InventoryOutboundDetailVo> voList = GenerateOutListWithPickingList(produceList,storageDetailMap,storageUnitMap,storageBatchMap);
        detailVos.addAll(voList);
        inventoryOutbound.setDetailVoList(detailVos);
        return inventoryOutbound;
    }

    @Override
    public List<OutboundRequireDto> GenerateOutRequireList(Integer ticketId) {
        List<PickingTicketDetail> pickingTicketDetailList = pickingTicketDetailRepository.findByTicketId(ticketId);
        List<OutboundRequireDto> outList=new ArrayList<>();
        for(PickingTicketDetail pickingTicketDetail:pickingTicketDetailList){
            OutboundRequireDto dto=new OutboundRequireDto(pickingTicketDetail.getProductId(),pickingTicketDetail.getMaterialNo(),pickingTicketDetail.getMaterialName(),pickingTicketDetail.getSpecification(),pickingTicketDetail.getCountUnit(),pickingTicketDetail.getCount().intValue(),pickingTicketDetail.getType().equals(OrderFormDetail.PRODUCE_TYPE_D)?PickingTicket.CUSTOMER_MATERIAL_FLAY_Y:PickingTicket.CUSTOMER_MATERIAL_FLAY_N);
            outList.add(dto);
        }
        return outList;
    }

    @Override
    public PickingTicket FindById(Integer id) {
        return pickingTicketRepository.findById(id).orElse(null);
    }

    private List<InventoryOutboundDetailVo> GenerateOutListWithInBoundList(String inBoundNo,List<PickingTicketDetail> oemList,Map<Integer, List<StorageDetail>> storageDetailMap) {
        InventoryInbound inventoryInbound = iInventoryInboundRepository.findByFormNo(inBoundNo);
        List<InventoryInboundDetail> inventoryInboundDetails = iInventoryInboundDetailRepository.findByFormNo(inBoundNo);
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        Map<Integer,PickingTicketDetail> pickingTicketDetailMap=new HashMap<>();
        for(PickingTicketDetail detail:oemList){
            pickingTicketDetailMap.put(detail.getProductId(),detail);
        }
        for (InventoryInboundDetail inboundDetail : inventoryInboundDetails) {
            InventoryOutboundDetailVo outboundDetailVo = new InventoryOutboundDetailVo();
            outboundDetailVo.setMaterialId(inboundDetail.getMaterialId());
            outboundDetailVo.setBatchNo(inventoryInbound.getBatchNo());
            outboundDetailVo.setMaterialNo(inboundDetail.getMaterialNo());
            outboundDetailVo.setName(inboundDetail.getMaterialName());
            outboundDetailVo.setSpecification(inboundDetail.getSpecification());
            PickingTicketDetail detail=pickingTicketDetailMap.get(inboundDetail.getMaterialId());
            outboundDetailVo.setCount(detail.getCount().intValue());
            outboundDetailVo.setUnit(detail.getCountUnit());
            outboundDetailVo.setOutCount(inboundDetail.getCount().intValue());
            outboundDetailVo.setOutCountUnit(inboundDetail.getUnit());
            outboundDetailVo.setStorageCode(inboundDetail.getStorageCode());
//            StorageBatchVo existOne = stringStorageBatchVoMap.get(inboundDetail.getMaterialId() + inventoryInbound.getBatchNo() + inboundDetail.getUnit());
//            if (existOne != null) {
//                outboundDetailVo.setStorageCount(existOne.getBatchCount());
//            }
            SetStorageDesAndStorageCode(storageDetailMap.get(inboundDetail.getMaterialId()),outboundDetailVo);
            voList.add(outboundDetailVo);
        }

        return voList;
    }

    private List<InventoryOutboundDetailVo> GenerateOutListWithPickingList(List<PickingTicketDetail> produceList,Map<Integer, List<StorageDetail>> storageDetailMap,Map<Integer, List<StorageUnit>> storageUnitMap,Map<Integer, List<StorageBatch>> storageBatchMap) {
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        for (PickingTicketDetail detail : produceList) {
            //????????????????????? ?????????????????????????????? ??????????????????
            Integer pTotalCount = ComputedTotalStorage(storageDetailMap.get(detail.getProductId()), detail.getCountUnit(), storageUnitMap.get(detail.getProductId()));
            //???????????????????????? ??????????????????
            if (pTotalCount >= detail.getCount().intValue()) {
                //?????????????????????  ????????????????????????
                List<InventoryOutboundDetailVo> matchBatchOunBoundList = GenerateOutBoundListWithStorageBatch(storageBatchMap.get(detail.getProductId()), detail, storageUnitMap.get(detail.getProductId()));
                //??????????????????????????????????????????
                if (matchBatchOunBoundList == null) {
                    List<InventoryOutboundDetailVo> outVos = GenerateOutBoundListWithStorageDetail(storageDetailMap.get(detail.getProductId()), detail.getCount().intValue(), detail.getCountUnit(), detail, storageUnitMap.get(detail.getProductId()));
                    SetStorageDesAndStorageCode(storageDetailMap.get(detail.getProductId()), outVos);
                    voList.addAll(outVos);
                } else {
                    //???????????? ??????????????????
                    SetStorageDesAndStorageCode(storageDetailMap.get(detail.getProductId()), matchBatchOunBoundList);
                    voList.addAll(matchBatchOunBoundList);
                }
            } else {
                //??????????????????
                InventoryOutboundDetailVo vo = new InventoryOutboundDetailVo();
                vo.setMaterialNo(detail.getMaterialNo());
                vo.setName(detail.getMaterialName());
                vo.setSpecification(detail.getSpecification());
                vo.setCount(detail.getCount().intValue());
                vo.setUnit(detail.getCountUnit());
                vo.setMaterialId(detail.getProductId());
                //???????????? ??????
                vo.setStorageFlag(0);
                SetStorageDesAndStorageCode(storageDetailMap.get(detail.getProductId()),vo);
                voList.add(vo);
            }
        }
        return voList;
    }


    //????????????????????? ???????????????????????????baseUnit???????????????StorageBatchVo ????????????baseUnit ??????
    private Integer ComputedTotalStorage(List<StorageDetail> storageDetails, String baseUnit, List<StorageUnit> unitList) {
        Integer totalStorage = 0;
        //?????????
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
                    logger.error("????????????error: ?????????????????????[{}]", storageDetail.getUnit());
                    continue;
                }
                totalStorage += (storageDetail.getCount() * packageUnit.getBaseUnitConvertCount());
            }
        }
        return totalStorage;
    }

    private List<InventoryOutboundDetailVo> GenerateOutBoundListWithStorageDetail(List<StorageDetail> storageDetails, Integer outCount, String baseUnit, PickingTicketDetail detail, List<StorageUnit> unitList) {
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        if (unitList == null) {
            //???????????????????????? ??????????????????
            InventoryOutboundDetailVo vo = GenerateWithBaseInfoPickDetail(detail);
            StorageDetail storageDetail = storageDetails.get(0);
            vo.setOutCount(outCount);
            vo.setOutCountUnit(storageDetail.getUnit());
            vo.setStorageCode(storageDetail.getStorageCode());
            vo.setStorageFlag(1);
            vo.setStorageDes(storageDetail.getCount() + storageDetail.getUnit());
            voList.add(vo);
            return voList;
        } else {
            //????????????????????? ????????????????????????????????????
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
                InventoryOutboundDetailVo vo = GenerateWithBaseInfoPickDetail(detail);
                vo.setOutCountUnit(storageDetail.getUnit());
                vo.setStorageCode(storageDetail.getStorageCode());
                vo.setStorageFlag(1);
                //??????????????????
                Integer needCount = outCount - count;
                //??????????????????
                Boolean sameUnit = storageDetail.getUnit().equals(baseUnit);
                if (sameUnit) {
                    count += storageDetail.getCount();
                } else {
                    StorageUnit packageUnit = packageUnitMap.get(storageDetail.getUnit());
                    if (packageUnit == null) {
                        logger.error("??????????????????error: ?????????????????????[{}]", storageDetail.getUnit());
                        continue;
                    }
                    count += (storageDetail.getCount() * packageUnit.getBaseUnitConvertCount());
                }
                //????????????????????????????????????
                if (count > outCount) {
                    if (sameUnit) {
                        //??????????????????  ??????????????? ??????????????????????????????
                        vo.setOutCount(needCount);
                    } else {
                        //????????????
                        StorageUnit packageUnit = packageUnitMap.get(storageDetail.getUnit());
                        //????????????
                        Double outUnitCount = Math.ceil(new Double(needCount) / packageUnit.getBaseUnitConvertCount());
                        //?????????????????? ??????????????????10???
                        Integer pickCount = needCount % packageUnit.getBaseUnitConvertCount();
                        vo.setOutCount(outUnitCount.intValue());
                        vo.setUnPackageCount(1);
                        vo.setUnPackageCountUnit(storageDetail.getUnit());
                        vo.setUnPackagePickCount(pickCount);
                    }
                    voList.add(vo);
                    return voList;
                } else {
                    //???????????? ???????????????????????????
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


    private InventoryOutboundDetailVo GenerateWithBaseInfoPickDetail(PickingTicketDetail detail) {
        InventoryOutboundDetailVo vo = new InventoryOutboundDetailVo();
        vo.setMaterialNo(detail.getMaterialNo());
        vo.setName(detail.getMaterialName());
        vo.setSpecification(detail.getSpecification());
        vo.setCount(detail.getCount().intValue());
        vo.setUnit(detail.getCountUnit());
        vo.setMaterialId(detail.getProductId());
        return vo;
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


    //???????????????????????? ??????????????????
    private List<InventoryOutboundDetailVo> GenerateOutBoundListWithStorageBatch(List<StorageBatch> existList, PickingTicketDetail detail, List<StorageUnit> unitList) {
        if (existList == null) return null;
        List<InventoryOutboundDetailVo> voList = new ArrayList<>();
        //?????????????????? ????????????
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
            InventoryOutboundDetailVo vo = GenerateWithBaseInfoPickDetail(detail);
            vo.setOutCountUnit(storageBatch.getCountUnit());
            vo.setStorageFlag(1);
            vo.setBatchNo(storageBatch.getBatchNo());
            //??????????????????
            Boolean sameUnit = storageBatch.getCountUnit().equals(detail.getCountUnit());
            Integer needCount = detail.getCount().intValue() - count;
            if (sameUnit) {
                count += storageBatch.getCount();
            } else {
                StorageUnit packageUnit = packageUnitMap.get(storageBatch.getCountUnit());
                if (packageUnit == null) {
                    logger.error("????????????error: ?????????????????????[{}]", storageBatch.getCountUnit());
                    continue;
                }
                count += (storageBatch.getCount() * packageUnit.getBaseUnitConvertCount());
            }
            if (count < detail.getCount().intValue()) {
                //??????????????? ????????????
                vo.setOutCount(storageBatch.getCount());
            } else if (count == detail.getCount().intValue()) {
                //????????????????????????
                vo.setOutCount(storageBatch.getCount());
                voList.add(vo);
                return voList;
            } else {
                if (sameUnit) {
                    //????????????????????????
                    vo.setOutCount(needCount);
                } else {
                    //??????
                    StorageUnit packageUnit = packageUnitMap.get(storageBatch.getCountUnit());
                    //????????????
                    Double outUnitCount = Math.ceil(new Double(needCount) / packageUnit.getBaseUnitConvertCount());
                    //?????????????????? ??????????????????10???
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

    private PickingTicketDetail CreateTicketDetailWithProcess(ProducePlanProcess process, ProducePlanDetail
            producePlanDetail, Integer ticketId) {
        PickingTicketDetail detail = new PickingTicketDetail();
        String detailType = producePlanDetail.getProduceType().equals(OrderFormDetail.PRODUCE_TYPE_D) ? producePlanDetail.getProduceType() : process.getDelegateFlag().equals(ProducePlanProcess.DELEGATE_Y) ? "W" : "P";
        detail.setType(detailType);
        detail.setProductId(process.getProductId());
        detail.setMaterialNo(process.getMaterialNo());
        detail.setMaterialName(process.getMaterialName());
        detail.setTicketId(ticketId);
        detail.setRemark(producePlanDetail.getRemark());
        detail.setCount(process.getMaterialCount().multiply(producePlanDetail.getCount()));
        detail.setCountUnit(producePlanDetail.getCountUnit());
        detail.setCreateDate(new Date());
        detail.setDelegateFlag(process.getDelegateFlag());
        detail.setState(PickingTicketDetail.STATE_CREATE);
        detail.setSpecification(producePlanDetail.getSpecification());
        return detail;
    }

    private List<BomVo> GetRootList(List<BomVo> voList) {
        Set<String> partNoSet = new HashSet<>();
        voList.forEach(bomVo -> partNoSet.add(bomVo.getPartNo()));
        List<BomVo> rootList = new ArrayList<>();
        for (BomVo vo : voList) {
            String key = vo.getChPartNo();
            if (partNoSet.contains(key)) continue;
            rootList.add(vo);
        }
        return rootList;
    }

    private PickingTicketDetail CreateTicketWithBomVo(BomVo bomVo, OrderFormDetail formDetail, Integer ticketId) {
        PickingTicketDetail detail = new PickingTicketDetail();
        detail.setType(OrderFormDetail.PRODUCE_TYPE_W);
        detail.setTicketId(ticketId);
        detail.setMaterialNo(bomVo.getChPartNo());
        detail.setMaterialName(bomVo.getName());
        detail.setSpecification(bomVo.getSpecification());
        detail.setCount(bomVo.getChQty().multiply(new BigDecimal(formDetail.getCount())));
        detail.setCountUnit(bomVo.getUnit());
        detail.setProductId(formDetail.getProductId());
        detail.setState(PickingTicketDetail.STATE_CREATE);
        return detail;
    }
}
