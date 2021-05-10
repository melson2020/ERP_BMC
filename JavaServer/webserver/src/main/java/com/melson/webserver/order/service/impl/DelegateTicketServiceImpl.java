package com.melson.webserver.order.service.impl;

import com.melson.webserver.order.dao.IDelegateDetailRepository;
import com.melson.webserver.order.dao.IDelegateTicketRepository;
import com.melson.webserver.order.entity.DelegateDetail;
import com.melson.webserver.order.entity.DelegateTicket;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.service.IDelegateTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public DelegateTicket GenerateTicket(List<OrderFormDetail> detailList, OrderForm form) {
       if(detailList==null||detailList.size()<=0)return null;
       DelegateTicket ticket=new DelegateTicket();
       ticket.setOrderFormId(form.getId());
       ticket.setOrderFormNo(form.getFormNo());
       ticket.setCustomerNo(form.getCustomerNo());
       ticket.setCustomerName(form.getCustomerName());
       ticket.setContractNo(form.getContractNo());
       ticket.setTicketNo("W"+System.currentTimeMillis());
       delegateTicketRepository.saveAndFlush(ticket);
       List<DelegateDetail> delegateDetails=new ArrayList<>();
       for (OrderFormDetail detail:detailList){
           DelegateDetail delegateDetail=CreateDelegateDetail(detail,ticket);
           delegateDetails.add(delegateDetail);
       }
        delegateDetailRepository.saveAll(delegateDetails);
       return ticket;
    }

    private DelegateDetail CreateDelegateDetail(OrderFormDetail orderFormDetail,DelegateTicket ticket){
        DelegateDetail delegateDetail=new DelegateDetail();
        delegateDetail.setDelegateTicketId(ticket.getId());
        delegateDetail.setObjectName(orderFormDetail.getProductName());
        delegateDetail.setRemark(orderFormDetail.getRemark());
        delegateDetail.setSpecification(orderFormDetail.getSpecification());
        delegateDetail.setCount(new BigDecimal(orderFormDetail.getCount()));
        delegateDetail.setCountUnit(orderFormDetail.getCountUnit());
        delegateDetail.setObjectNo(orderFormDetail.getProductNo());
        return delegateDetail;
    }
}
