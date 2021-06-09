package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author Nelson
 * @Description 生产计划表
 * @Date 2021/5/6
 */
@Entity
@Table(name = "produce_plan")
public class ProducePlan {
    public static  final  String CREATED="1";
    public static  final  String PROCESSING="2";
    public static  final  String INBOUND="3";
    public static  final  String COMPLETED="4";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderFormId;
    private String orderFormNo;
    private String customerNo;
    private String customerName;
    private String contractNo;
    private String pickingTicketNo;
    private String type;
    private String planNo;
    private Date createDate;
    // 1创建 2执行 3完成
    private String state;

    private Date startDate;

    private Date endDate;

    private String inboundTicketNo;

    private String outboundTicketNo;

    public String getInboundTicketNo() {
        return inboundTicketNo;
    }

    public void setInboundTicketNo(String inboundTicketNo) {
        this.inboundTicketNo = inboundTicketNo;
    }

    public String getOutboundTicketNo() {
        return outboundTicketNo;
    }

    public void setOutboundTicketNo(String outboundTicketNo) {
        this.outboundTicketNo = outboundTicketNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Integer orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getOrderFormNo() {
        return orderFormNo;
    }

    public void setOrderFormNo(String orderFormNo) {
        this.orderFormNo = orderFormNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
