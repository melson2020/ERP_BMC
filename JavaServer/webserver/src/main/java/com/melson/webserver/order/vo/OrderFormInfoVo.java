package com.melson.webserver.order.vo;

import com.melson.webserver.order.entity.*;

import java.util.List;

/**
 * @Author Nelson
 * @Description order form 所有详细信息载体 包含 formDetail produce plan, delegate ticket,purchase plan
 * @Date 2021/5/18
 */
public class OrderFormInfoVo {
    private OrderForm orderForm;
    private List<OrderFormDetail> orderFormDetailList;
    private ProducePlan producePlan;
    private DelegateTicket delegateTicket;
    private List<PurchaseDetail> purchaseDetailList;

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }

    public List<OrderFormDetail> getOrderFormDetailList() {
        return orderFormDetailList;
    }

    public void setOrderFormDetailList(List<OrderFormDetail> orderFormDetailList) {
        this.orderFormDetailList = orderFormDetailList;
    }

    public ProducePlan getProducePlan() {
        return producePlan;
    }

    public void setProducePlan(ProducePlan producePlan) {
        this.producePlan = producePlan;
    }

    public DelegateTicket getDelegateTicket() {
        return delegateTicket;
    }

    public void setDelegateTicket(DelegateTicket delegateTicket) {
        this.delegateTicket = delegateTicket;
    }

    public List<PurchaseDetail> getPurchaseDetailList() {
        return purchaseDetailList;
    }

    public void setPurchaseDetailList(List<PurchaseDetail> purchaseDetailList) {
        this.purchaseDetailList = purchaseDetailList;
    }
}
