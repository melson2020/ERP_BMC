package com.melson.webserver.order.service.impl;

import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.vo.BomProcessVo;
import com.melson.webserver.dict.vo.BomVo;
import com.melson.webserver.order.dao.IPickingTicketDetailRepository;
import com.melson.webserver.order.dao.IPickingTicketRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.resource.OrderFormResource;
import com.melson.webserver.order.service.IPickingTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        pickingTicketRepository.saveAndFlush(ticket);
        Map<Integer, ProducePlanDetail> detailIdMap = new HashMap<>(detailList.size());
        for (ProducePlanDetail detail : detailList) {
            detailIdMap.put(detail.getProductId(), detail);
        }
        List<PickingTicketDetail> ticketDetails = new ArrayList<>();
        List<ProducePlanProcess> rootList=GetProducePlanProcessRootList(processList);
        for (ProducePlanProcess p : rootList) {
            ticketDetails.add(CreateTicketDetailWithProcess(p, detailIdMap.get(p.getProductId()), ticket.getId()));
        }
        pickingTicketDetailRepository.saveAll(ticketDetails);
        return ticket;
    }

    private List<ProducePlanProcess> GetProducePlanProcessRootList( List<ProducePlanProcess> list){
        List<ProducePlanProcess> rootList=new ArrayList<>();
        Set<String> noSet=new HashSet<>();
        list.forEach(process -> {
            if(!noSet.contains(process.getPartMaterialNo())){
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
                logger.warn("orderFormDetail 委外生产领料单 未获取到bomNos, detail id" + detail.getId());
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
        return pickingTicketRepository.findBySourceIdAndType(planId,PickingTicket.TYPE_PLAN);
    }

    @Override
    public PickingTicket FindBySourceIdAndType(Integer sourceId,String type) {
        return pickingTicketRepository.findBySourceIdAndType(sourceId,type);
    }

    private PickingTicketDetail CreateTicketDetailWithProcess(ProducePlanProcess process, ProducePlanDetail
            producePlanDetail, Integer ticketId) {
        PickingTicketDetail detail = new PickingTicketDetail();
        String detailType = producePlanDetail.getProduceType().equals(OrderFormDetail.PRODUCE_TYPE_D) ? producePlanDetail.getProduceType() : process.getDelegateFlag().equals(ProducePlanProcess.DELEGATE_Y) ? "W" : "P";
        detail.setType(detailType);
        detail.setMaterialNo(process.getMaterialNo());
        detail.setMaterialName(process.getMaterialName());
        detail.setTicketId(ticketId);
        detail.setRemark(producePlanDetail.getRemark());
        detail.setCount(process.getMaterialCount().multiply(producePlanDetail.getCount()));
        detail.setCountUnit(producePlanDetail.getCountUnit());
        detail.setCreateDate(new Date());
        detail.setDelegateFlag(process.getDelegateFlag());
        detail.setState(PickingTicketDetail.STATE_CREATE);
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
        detail.setState(PickingTicketDetail.STATE_CREATE);
        return detail;
    }
}
