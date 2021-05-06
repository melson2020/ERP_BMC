package com.melson.webserver.order.vo;

import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/6
 */
public class OrderFormConfirmVo {
    private OrderForm orderForm;
    List<OrderFormDetail> orderFormDetails;

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }

    public List<OrderFormDetail> getOrderFormDetails() {
        return orderFormDetails;
    }

    public void setOrderFormDetails(List<OrderFormDetail> orderFormDetails) {
        this.orderFormDetails = orderFormDetails;
    }
}
