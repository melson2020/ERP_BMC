package com.melson.webserver.order.service.impl;

import com.melson.base.utils.DateUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.vo.InventoryInboundDetailVo;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.order.dao.IDelegateDetailRepository;
import com.melson.webserver.order.dao.IPickingTicketDetailRepository;
import com.melson.webserver.order.dao.IProducePlanDetailRepository;
import com.melson.webserver.order.dao.IProducePlanRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IDelegateTicketService;
import com.melson.webserver.order.service.IPickingTicketService;
import com.melson.webserver.order.service.IProducePlanProcessService;
import com.melson.webserver.order.service.IProducePlanService;
import com.melson.webserver.produce.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Service
public class ProducePlanServiceImpl implements IProducePlanService {
    private static final Logger logger = LoggerFactory.getLogger(ProducePlanServiceImpl.class);

    @Autowired
    private IProducePlanRepository producePlanRepository;
    @Autowired
    private IProducePlanDetailRepository producePlanDetailRepository;
    @Autowired
    private IProducePlanProcessService producePlanProcessService;
    @Autowired
    private IDelegateTicketService delegateTicketService;
    @Autowired
    private IDelegateDetailRepository delegateDetailRepository;
    @Autowired
    private IPickingTicketService pickingTicketService;
    @Autowired
    private IPickingTicketDetailRepository pickingTicketDetailRepository;

    /**
     * @param details
     * @param form    订单下达 生成生产计划 同时生成工序列表
     * @return
     */
    @Override
    public ProducePlan GeneratePlan(List<OrderFormDetail> details, OrderForm form) {
        if (details == null || details.size() <= 0) return null;
        ProducePlan pp = new ProducePlan();
        for (OrderFormDetail detail : details) {
            ProducePlan producePlan = new ProducePlan();
            producePlan.setOrderFormId(form.getId());
            producePlan.setOrderFormNo(form.getFormNo());
            producePlan.setCustomerNo(form.getCustomerNo());
            producePlan.setCustomerName(form.getCustomerName());
            producePlan.setContractNo(form.getContractNo());
            producePlan.setPlanNo("PL" + System.currentTimeMillis());
            producePlan.setCreateDate(new Date());
            producePlan.setState(ProducePlan.CREATED);
            producePlan.setType(detail.getProduceType());
            ProducePlan savedPP = producePlanRepository.saveAndFlush(producePlan);
            ProducePlanDetail producePlanDetail=CreateProducePlanDetail(detail);
            producePlanDetail.setProducePlanId(savedPP.getId());
            ProducePlanDetail savePPD=producePlanDetailRepository.save(producePlanDetail);
            //生成工序记录
            producePlanProcessService.GeneratePlanProcessSeperately(savedPP, savePPD);
        }
        return pp;

//        ProducePlan producePlan = new ProducePlan();
//        producePlan.setOrderFormId(form.getId());
//        producePlan.setOrderFormNo(form.getFormNo());
//        producePlan.setCustomerNo(form.getCustomerNo());
//        producePlan.setCustomerName(form.getCustomerName());
//        producePlan.setContractNo(form.getContractNo());
//        producePlan.setPlanNo("PL" + System.currentTimeMillis());
//        producePlan.setCreateDate(new Date());
//        producePlan.setState(ProducePlan.CREATED);
//
//        List<ProducePlanDetail> producePlanDetails = new ArrayList<>();
//        String producePlanType = "";
//        for (OrderFormDetail detail : details) {
//            producePlanDetails.add(CreateProducePlanDetail(detail));
//            if (!producePlanType.contains(detail.getProduceType())) {
//                producePlanType += (detail.getProduceType());
//            }
//        }
//        producePlan.setType(producePlanType);
//        producePlanRepository.saveAndFlush(producePlan);
//        producePlanDetails.forEach(producePlanDetail -> {
//            producePlanDetail.setProducePlanId(producePlan.getId());
//        });
//        List<ProducePlanDetail> savedDetails = producePlanDetailRepository.saveAll(producePlanDetails);
//        //生成工序记录
//        producePlanProcessService.GeneratePlanProcess(producePlan, savedDetails);
//        return producePlan;
    }

    @Override
    public List<ProducePlan> FindPlanList(String state) {
        return producePlanRepository.findByState(state);
    }

    @Override
    public List<ProducePlanDetail> FindPlanDetailList(Integer planId) {
        List<ProducePlanDetail> list=producePlanDetailRepository.findByProducePlanId(planId);
        for(ProducePlanDetail ppd:list)
        {
            List<ProducePlanProcessVo> pppList=producePlanProcessService.FindPlanProcess(planId,ppd.getId());
            ppd.setProcessList(pppList);
        }
        return list;
    }

    @Override
    public ProducePlan UpdatePlan(ProducePlan plan, Boolean confirm) {
        if (confirm) {
            //生成委外单（工序），同时生成领料单
            plan.setState(ProducePlan.PROCESSING);
            List<ProducePlanProcess> processList = producePlanProcessService.FindByPlanId(plan.getId());
            List<ProducePlanDetail> planDetails = producePlanDetailRepository.findByProducePlanId(plan.getId());
            List<ProducePlanProcess> delegateList = new ArrayList<>();
            for (ProducePlanProcess p : processList) {
                if (p.getDelegateFlag().equals(ProducePlanProcess.DELEGATE_Y)) {
                    delegateList.add(p);
                }
            }
            //根据delegateList 生成委外明细
            DelegateTicket ticket = delegateTicketService.GenerateTicketWithProcess(delegateList, plan, planDetails);
            //根据所有工序 生成取料明细 如有委外 则在明细中表明委外
            PickingTicket pickingTicket = pickingTicketService.GeneratePickTicketWithPlanProcess(processList, plan, planDetails);
            plan.setPickingTicketNo(pickingTicket.getTicketNo());
        }
        return producePlanRepository.save(plan);
    }

    @Override
    public ProducePlan FindByOrderFormId(Integer orderFormId) {
        return producePlanRepository.findByOrderFormId(orderFormId);
    }

    @Override
    public ProducePlanStateSummaryVo GetStateSummary() {
        List<Object[]> objList = producePlanRepository.findStateSummary();
        Map<String, Integer> stateCountMap = new HashMap<>();
        for (Object[] obj : objList) {
            stateCountMap.put(obj[0].toString(), Integer.parseInt(obj[1].toString()));
        }
        ProducePlanStateSummaryVo vo = new ProducePlanStateSummaryVo(stateCountMap.get(ProducePlan.CREATED) == null ? 0 : stateCountMap.get(ProducePlan.CREATED), stateCountMap.get(ProducePlan.PROCESSING) == null ? 0 : stateCountMap.get(ProducePlan.PROCESSING));
        return vo;
    }

    @Override
    public List<ProducePlanPickingTicketVo> GetProcessingPlanAndPickingTicketInfo() {
        List<Object[]> objList = producePlanRepository.findProcessingPlanAndPickingTicket();
        List<ProducePlanPickingTicketVo> list = EntityUtils.castEntity(objList, ProducePlanPickingTicketVo.class, new ProducePlanPickingTicketVo());
        return list;
    }

    @Override
    public ProducePlanInfoVo FindPlanInfo(Integer planId) {
        ProducePlanInfoVo infoVo = new ProducePlanInfoVo();
        ProducePlan producePlan = producePlanRepository.findById(planId).orElse(null);
        infoVo.setProducePlan(producePlan);
        List<ProducePlanDetail> details = producePlanDetailRepository.findByProducePlanId(planId);
        for(ProducePlanDetail ppd:details)
        {
            List<ProducePlanProcessVo> pppList=producePlanProcessService.FindPlanProcess(planId,ppd.getId());
            ppd.setProcessList(pppList);
        }
        infoVo.setPlanDetails(details);
        DelegateTicket delegateTicket = delegateTicketService.FindByPlanId(planId);
        if (delegateTicket != null) {
            List<DelegateDetail> delegateDetails = delegateDetailRepository.findByDelegateTicketId(delegateTicket.getId());
            infoVo.setDelegateDetailList(delegateDetails);
        }
        infoVo.setDelegateTicket(delegateTicket);
        PickingTicket pickingTicket = pickingTicketService.FindByPlanId(planId);
        if (pickingTicket != null) {
            infoVo.setPickingTicket(pickingTicket);
            List<PickingTicketDetail> pickingTicketDetailList = pickingTicketDetailRepository.findByTicketId(pickingTicket.getId());
            infoVo.setPickingTicketDetails(pickingTicketDetailList);
        }
        return infoVo;
    }

    @Override
    public List<ProducePlan> FindProducePlanRecord(Map<String, String[]> kvMap) {

        List<ProducePlan> recordList = producePlanRepository.findAll(new Specification<ProducePlan>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<ProducePlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("state"), "1"));
                for (String key : kvMap.keySet()) {
                    if (StringUtils.isEmpty(kvMap.get(key)[0])) continue;
                    if (key.contains("createDate")) {
                        try {
                            Date createDate = new SimpleDateFormat("yyyy-MM-dd").parse(kvMap.get(key)[0]);
                            predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get(key), createDate));
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(createDate);
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            Date newDate = calendar.getTime();
                            predicate.getExpressions().add(criteriaBuilder.lessThan(root.get(key), newDate));
                        } catch (ParseException e) {
                            continue;
                        }
                    } else {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get(key), kvMap.get(key)[0]));
                    }
                }
                return predicate;
            }
        });
        return recordList;
    }

    @Override
    public InventoryInboundVo GenerateInventoryInBound(Integer ticketId, Integer userId) {
        ProducePlan producePlan=producePlanRepository.findById(ticketId).orElse(null);
        List<Object[]> producePlanDetails=producePlanDetailRepository.findInventoryInBoundDetail(producePlan.getId());
        List<InventoryInboundDetailVo> detailVos=EntityUtils.castEntity(producePlanDetails,InventoryInboundDetailVo.class,new InventoryInboundDetailVo());;
        InventoryInboundVo inventoryInbound=new InventoryInboundVo();
        inventoryInbound.setFormNo(InventoryInbound.CODE_PREFIX+ System.currentTimeMillis());
        inventoryInbound.setBatchNo(DateUtil.timeShortFormat(new Date()));
        inventoryInbound.setType(InventoryInbound.TYPE_PRODUCE);
        inventoryInbound.setSourceNo(producePlan.getPlanNo());
        inventoryInbound.setSourceId(ticketId);
        inventoryInbound.setCreateDate(new Date());
        inventoryInbound.setCreateUser(userId);
        inventoryInbound.setDetailVoList(detailVos);
        return inventoryInbound;
    }

    @Override
    public int UpdatePlanAfterInBound(Integer id, String inboundNo, String state) {
        return producePlanRepository.UpdatePlanAfterInBound(id,inboundNo,state);
    }

    private ProducePlanDetail CreateProducePlanDetail(OrderFormDetail detail) {
        ProducePlanDetail producePlanDetail = new ProducePlanDetail();
        producePlanDetail.setProductId(detail.getProductId());
        producePlanDetail.setRemark(detail.getRemark());
        producePlanDetail.setProductName(detail.getProductName());
        producePlanDetail.setSpecification(detail.getSpecification());
        producePlanDetail.setCount(new BigDecimal(detail.getCount()));
        producePlanDetail.setCountUnit(detail.getCountUnit());
        producePlanDetail.setProduceType(detail.getProduceType());
        producePlanDetail.setBomNo(detail.getBomNo());
        producePlanDetail.setMaterialNo(detail.getProductNo());
        String bomNos = "";
        if (detail.getBomNos() != null && detail.getBomNos().size() > 0) {
            for (String bomId : detail.getBomNos()) {
                bomNos = StringUtils.isEmpty(bomNos) ? bomId.toString() : bomNos + "-" + bomId;
            }
        }
        producePlanDetail.setBomNos(bomNos);
        return producePlanDetail;
    }


}
