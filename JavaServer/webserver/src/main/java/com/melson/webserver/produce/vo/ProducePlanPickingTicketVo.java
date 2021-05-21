package com.melson.webserver.produce.vo;

import java.util.Date;

/**
 * @Author Nelson
 * @Description 生产计划附带领料单详细
 * @Date 2021/5/20
 */
public class ProducePlanPickingTicketVo {
    private Integer id;
    private String planNo;
    private String orderFormNo;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String pickingTicketNo;
    private String pickingState;
    private String planState;

    public ProducePlanPickingTicketVo(){

    }

    public ProducePlanPickingTicketVo(Integer id, String planNo, String orderFormNo, Date createDate, Date startDate, Date endDate, String pickingTicketNo, String pickingState, String planState) {
        this.id = id;
        this.planNo = planNo;
        this.orderFormNo = orderFormNo;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickingTicketNo = pickingTicketNo;
        this.pickingState = pickingState;
        this.planState = planState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getOrderFormNo() {
        return orderFormNo;
    }

    public void setOrderFormNo(String orderFormNo) {
        this.orderFormNo = orderFormNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPickingTicketNo() {
        return pickingTicketNo;
    }

    public void setPickingTicketNo(String pickingTicketNo) {
        this.pickingTicketNo = pickingTicketNo;
    }

    public String getPickingState() {
        return pickingState;
    }

    public void setPickingState(String pickingState) {
        this.pickingState = pickingState;
    }

    public String getPlanState() {
        return planState;
    }

    public void setPlanState(String planState) {
        this.planState = planState;
    }
}
