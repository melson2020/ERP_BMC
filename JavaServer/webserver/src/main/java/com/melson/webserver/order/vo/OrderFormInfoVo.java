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
    private List<ProducePlan> producePlanList;
    private List<DelegateTicket> delegateTicketList;
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

    public List<ProducePlan> getProducePlanList() {
        return producePlanList;
    }

    public void setProducePlanList(List<ProducePlan> producePlanList) {
        this.producePlanList = producePlanList;
    }

    public List<DelegateTicket> getDelegateTicketList() {
        return delegateTicketList;
    }

    public void setDelegateTicketList(List<DelegateTicket> delegateTicketList) {
        this.delegateTicketList = delegateTicketList;
    }

    public List<PurchaseDetail> getPurchaseDetailList() {
        return purchaseDetailList;
    }

    public void setPurchaseDetailList(List<PurchaseDetail> purchaseDetailList) {
        this.purchaseDetailList = purchaseDetailList;
    }
}
