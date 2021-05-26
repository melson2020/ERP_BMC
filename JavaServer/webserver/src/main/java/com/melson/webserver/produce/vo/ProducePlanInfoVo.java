package com.melson.webserver.produce.vo;

import com.melson.webserver.order.entity.*;

import java.util.List;

/**
 * @Author Nelson
 * @Description 生产计划详细 包括 生产清单 领料单 入库单等
 * @Date 2021/5/26
 */
public class ProducePlanInfoVo {
    private ProducePlan producePlan;
    private List<ProducePlanDetail> planDetails;
    private DelegateTicket delegateTicket;
    private PickingTicket pickingTicket;
    private List<PickingTicketDetail> pickingTicketDetails;

    public List<ProducePlanDetail> getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(List<ProducePlanDetail> planDetails) {
        this.planDetails = planDetails;
    }

    public PickingTicket getPickingTicket() {
        return pickingTicket;
    }

    public void setPickingTicket(PickingTicket pickingTicket) {
        this.pickingTicket = pickingTicket;
    }

    public List<PickingTicketDetail> getPickingTicketDetails() {
        return pickingTicketDetails;
    }

    public void setPickingTicketDetails(List<PickingTicketDetail> pickingTicketDetails) {
        this.pickingTicketDetails = pickingTicketDetails;
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
}
