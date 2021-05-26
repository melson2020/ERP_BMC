package com.melson.webserver.order.service.impl;

import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.order.dao.IPickingTicketDetailRepository;
import com.melson.webserver.order.dao.IProducePlanDetailRepository;
import com.melson.webserver.order.dao.IProducePlanRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IDelegateTicketService;
import com.melson.webserver.order.service.IPickingTicketService;
import com.melson.webserver.order.service.IProducePlanProcessService;
import com.melson.webserver.order.service.IProducePlanService;
import com.melson.webserver.produce.vo.ProducePlanConfirmInfoVo;
import com.melson.webserver.produce.vo.ProducePlanInfoVo;
import com.melson.webserver.produce.vo.ProducePlanPickingTicketVo;
import com.melson.webserver.produce.vo.ProducePlanStateSummaryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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
        ProducePlan producePlan = new ProducePlan();
        producePlan.setOrderFormId(form.getId());
        producePlan.setOrderFormNo(form.getFormNo());
        producePlan.setCustomerNo(form.getCustomerNo());
        producePlan.setCustomerName(form.getCustomerName());
        producePlan.setContractNo(form.getContractNo());
        producePlan.setPlanNo("PL" + System.currentTimeMillis());
        producePlan.setCreateDate(new Date());
        producePlan.setState(ProducePlan.CREATED);

        List<ProducePlanDetail> producePlanDetails = new ArrayList<>();
        String producePlanType = "";
        for (OrderFormDetail detail : details) {
            producePlanDetails.add(CreateProducePlanDetail(detail));
            if (!producePlanType.contains(detail.getProduceType())) {
                producePlanType += (detail.getProduceType());
            }
        }
        producePlan.setType(producePlanType);
        producePlanRepository.saveAndFlush(producePlan);
        producePlanDetails.forEach(producePlanDetail -> {
            producePlanDetail.setProducePlanId(producePlan.getId());
        });
        List<ProducePlanDetail> savedDetails = producePlanDetailRepository.saveAll(producePlanDetails);
        //生成工序记录
        producePlanProcessService.GeneratePlanProcess(producePlan, savedDetails);
        return producePlan;
    }

    @Override
    public List<ProducePlan> FindPlanList(String state) {
        return producePlanRepository.findByState(state);
    }

    @Override
    public List<ProducePlanDetail> FindPlanDetailList(Integer planId) {
        return producePlanDetailRepository.findByProducePlanId(planId);
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
        infoVo.setPlanDetails(details);
        DelegateTicket delegateTicket = delegateTicketService.FindByPlanId(planId);
        infoVo.setDelegateTicket(delegateTicket);
        PickingTicket pickingTicket = pickingTicketService.FindByPlanId(planId);
        if (pickingTicket != null) {
            infoVo.setPickingTicket(pickingTicket);
            List<PickingTicketDetail> pickingTicketDetailList=pickingTicketDetailRepository.findByTicketId(pickingTicket.getId());
            infoVo.setPickingTicketDetails(pickingTicketDetailList);
        }
        return infoVo;
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
