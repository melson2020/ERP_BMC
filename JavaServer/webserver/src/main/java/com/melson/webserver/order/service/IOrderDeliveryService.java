package com.melson.webserver.order.service;

import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.entity.ContractOrg;
import com.melson.webserver.order.entity.OrderDelivery;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/16
 */
public interface IOrderDeliveryService {
    OrderDelivery FindByOrderFormNo(String orderFormNo);
    OrderDelivery CreateOrderDelivery(OrderForm form, List<OrderFormDetail> orderFormDetails, Contract contract, ContractOrg contractOrg);
    OrderDelivery Confirm(OrderDelivery orderDelivery);
    List<OrderDelivery> FindByState(String state);
}
