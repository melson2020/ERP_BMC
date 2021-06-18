package com.melson.webserver.order.service.impl;

import com.melson.base.utils.DateUtil;
import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.entity.ContractOrg;
import com.melson.webserver.inventory.dto.OutboundRequireDto;
import com.melson.webserver.order.dao.IOrderDeliveryDetailRepository;
import com.melson.webserver.order.dao.IOrderDeliveryRepository;
import com.melson.webserver.order.dao.IOrderFormRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IOrderDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/16
 */
@Service
public class OrderDeliveryServiceImpl implements IOrderDeliveryService {
    @Autowired
    private IOrderDeliveryRepository orderDeliveryRepository;
    @Autowired
    private IOrderDeliveryDetailRepository orderDeliveryDetailRepository;
    @Autowired
    private IOrderFormRepository orderFormRepository;
    @Override
    public OrderDelivery FindByOrderFormNo(String orderFormNo) {
         OrderDelivery existOne=orderDeliveryRepository.findByOrderFormNo(orderFormNo);
         if(existOne!=null){
             List<OrderDeliveryDetail> deliveryDetails=orderDeliveryDetailRepository.findByOrderDeliveryId(existOne.getId());
             existOne.setDeliveryDetails(deliveryDetails);
         }
         return  existOne;
    }

    @Override
    public OrderDelivery CreateOrderDelivery(OrderForm form, List<OrderFormDetail> orderFormDetails, Contract contract, ContractOrg contractOrg) {
        OrderDelivery orderDelivery=new OrderDelivery();
        orderDelivery.setTicketNo(OrderDelivery.CODE_PREFIX+ System.currentTimeMillis());
        orderDelivery.setState(OrderDelivery.STATE_CREATE);
        orderDelivery.setUserId(0);
        orderDelivery.setUserName("");
        orderDelivery.setCustomerNo(form.getCustomerNo());
        orderDelivery.setCustomerName(form.getCustomerName());
        orderDelivery.setOrderFormNo(form.getFormNo());
        orderDelivery.setDeliveryWay(contract.getDiliveryWay());
        orderDelivery.setAddress(contractOrg.getAddress());
        orderDelivery.setContactName(contractOrg.getContactUser());
        orderDelivery.setPhone(contractOrg.getPhone());
        orderDelivery.setContractNo(contract.getContractNo());
        orderDeliveryRepository.saveAndFlush(orderDelivery);
        List<OrderDeliveryDetail> deliveryDetails=GenerateOrderDeliveryDetail(orderFormDetails,orderDelivery);
        List<OrderDeliveryDetail> savedList=orderDeliveryDetailRepository.saveAll(deliveryDetails);
        orderDelivery.setDeliveryDetails(savedList);
        return orderDelivery;
    }

    @Override
    @Transactional
    public OrderDelivery Confirm(OrderDelivery orderDelivery) {
        orderDelivery.setDeliveryDate(new Date());
        orderDelivery.setState(OrderDelivery.STATE_CONFIRM);
        OrderForm orderForm=orderFormRepository.findByFormNo(orderDelivery.getOrderFormNo());
        orderForm.setState(OrderForm.STATE_OUTBOUND);
        orderFormRepository.save(orderForm);
        return orderDeliveryRepository.save(orderDelivery);
    }

    @Override
    public List<OrderDelivery> FindByState(String state) {
        return orderDeliveryRepository.findByState(state);
    }

    @Override
    public List<OutboundRequireDto> GenerateOutRequires(Integer ticketId) {
        List<OrderDeliveryDetail> deliveryDetails=orderDeliveryDetailRepository.findByOrderDeliveryId(ticketId);
        List<OutboundRequireDto> outboundRequireDtos=new ArrayList<>(deliveryDetails.size());
        for (OrderDeliveryDetail deliveryDetail:deliveryDetails){
            OutboundRequireDto dto=new OutboundRequireDto(deliveryDetail.getProductId(),deliveryDetail.getProductNo(),deliveryDetail.getName(),deliveryDetail.getSpecification(),deliveryDetail.getUnit(),deliveryDetail.getCount(), PickingTicket.CUSTOMER_MATERIAL_FLAY_N);
            outboundRequireDtos.add(dto);
        }
        return outboundRequireDtos;
    }

    @Override
    public OrderDelivery FindById(Integer id) {
        return orderDeliveryRepository.findById(id).orElse(null);
    }

    private List<OrderDeliveryDetail> GenerateOrderDeliveryDetail(List<OrderFormDetail> orderFormDetails,OrderDelivery orderDelivery){
        List<OrderDeliveryDetail> orderDeliveryDetails=new ArrayList<>();
        for(OrderFormDetail formDetail:orderFormDetails){
            OrderDeliveryDetail deliveryDetail=new OrderDeliveryDetail();
            deliveryDetail.setProductId(formDetail.getProductId());
            deliveryDetail.setName(formDetail.getProductName());
            deliveryDetail.setSpecification(formDetail.getSpecification());
            deliveryDetail.setCount(formDetail.getCount());
            deliveryDetail.setUnit(formDetail.getCountUnit());
            deliveryDetail.setOrderDeliveryId(orderDelivery.getId());
            deliveryDetail.setProductNo(formDetail.getProductNo());
            orderDeliveryDetails.add(deliveryDetail);
        }
        return orderDeliveryDetails;
    }
}
