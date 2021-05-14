package com.melson.webserver.order.service.impl;

import com.melson.webserver.order.dao.IDelegateDetailRepository;
import com.melson.webserver.order.dao.IDelegateTicketRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IDelegateTicketService;
import com.melson.webserver.order.service.IPickingTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Service
public class DelegateTicketServiceImpl implements IDelegateTicketService {
    private static final Logger logger = LoggerFactory.getLogger(DelegateTicketServiceImpl.class);
    @Autowired
    private IDelegateTicketRepository delegateTicketRepository;
    @Autowired
    private IDelegateDetailRepository delegateDetailRepository;
    @Autowired
    IPickingTicketService pickingTicketService;

    @Override
    /**
     * 订单委外 我方提供物料 故需要生成取料单
     */
    public DelegateTicket GenerateTicket(List<OrderFormDetail> detailList, OrderForm form) {
        if (detailList == null || detailList.size() <= 0) return null;
        DelegateTicket ticket = new DelegateTicket();
        ticket.setSourceId(form.getId());
        ticket.setSourceNo(form.getFormNo());
        ticket.setCustomerNo(form.getCustomerNo());
        ticket.setCustomerName(form.getCustomerName());
        ticket.setContractNo(form.getContractNo());
        ticket.setType(DelegateTicket.TYPE_ORDER);
        ticket.setTicketNo("W" + System.currentTimeMillis());
        delegateTicketRepository.saveAndFlush(ticket);
        List<DelegateDetail> delegateDetails = new ArrayList<>();
        for (OrderFormDetail detail : detailList) {
            DelegateDetail delegateDetail = CreateDelegateDetail(detail, ticket);
            delegateDetails.add(delegateDetail);
        }
        delegateDetailRepository.saveAll(delegateDetails);
        pickingTicketService.GeneratePickTicketWithOrderFormDetail(form,detailList);
        return ticket;
    }

    @Override
    public DelegateTicket GenerateTicketWithProcess(List<ProducePlanProcess> list, ProducePlan plan, List<ProducePlanDetail> detailList) {
        if (list == null || list.size() <= 0) return null;
        DelegateTicket ticket = new DelegateTicket();
        Map<Integer, ProducePlanDetail> detailIdMap = new HashMap<>(detailList.size());
        for (ProducePlanDetail detail : detailList) {
            detailIdMap.put(detail.getProductId(), detail);
        }
        ticket.setSourceId(plan.getId());
        ticket.setSourceNo(plan.getPlanNo());
        ticket.setCustomerNo(plan.getCustomerNo());
        ticket.setCustomerName(plan.getCustomerName());
        ticket.setContractNo(plan.getContractNo());
        ticket.setTicketNo("W" + System.currentTimeMillis());
        ticket.setType(DelegateTicket.TYPE_PLAN);
        delegateTicketRepository.saveAndFlush(ticket);
        List<DelegateDetail> delegateDetails = new ArrayList<>();
        for (ProducePlanProcess p : list) {
            delegateDetails.add(CreateDelegateDetailWithProcess(p, ticket.getId(), detailIdMap.get(p.getProductId())));
        }
        delegateDetailRepository.saveAll(delegateDetails);
        return ticket;
    }

    private DelegateDetail CreateDelegateDetail(OrderFormDetail orderFormDetail, DelegateTicket ticket) {
        DelegateDetail delegateDetail = new DelegateDetail();
        delegateDetail.setDelegateTicketId(ticket.getId());
        delegateDetail.setBomNo(orderFormDetail.getBomNo());
        delegateDetail.setObjectName(orderFormDetail.getProductName());
        delegateDetail.setRemark(orderFormDetail.getRemark());
        delegateDetail.setSpecification(orderFormDetail.getSpecification());
        delegateDetail.setCount(new BigDecimal(orderFormDetail.getCount()));
        delegateDetail.setCountUnit(orderFormDetail.getCountUnit());
        delegateDetail.setObjectNo(orderFormDetail.getProductNo());
        return delegateDetail;
    }

    private DelegateDetail CreateDelegateDetailWithProcess(ProducePlanProcess process, Integer ticketId, ProducePlanDetail producePlanDetail) {
        DelegateDetail delegateDetail = new DelegateDetail();
        delegateDetail.setDelegateTicketId(ticketId);
        delegateDetail.setObjectName(process.getProcessName());
        delegateDetail.setRemark(producePlanDetail.getRemark());
        delegateDetail.setSpecification(producePlanDetail.getSpecification());
        delegateDetail.setCount(producePlanDetail.getCount());
        delegateDetail.setCountUnit(producePlanDetail.getCountUnit());
        delegateDetail.setObjectNo(process.getProcessNo());
        delegateDetail.setBomNo(producePlanDetail.getBomNo());
        delegateDetail.setMaterialInfo(process.getMaterialName() + "x" + process.getMaterialCount());
        return delegateDetail;

    }
}
