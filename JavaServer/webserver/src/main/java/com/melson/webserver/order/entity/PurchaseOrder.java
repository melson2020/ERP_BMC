package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/6/5
 */
@Entity
@Table(name="purchase_order")
public class PurchaseOrder {
    public static final String PO_NO_CHAR="PO";

    public static final String PO_STATE_CREATE="CREATE";      //进行中
    public static final String PO_STATE_COMPLETE="COMPLETE";  //已入库
    public static final String PO_STATE_CANCEL="CANCEL";      //取消


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String poNo;
    private String state;   // 状态值  如; 进行中（CREATE），已入库(COMPLETE) ； 取消 （CANCEL）
    private Integer supplyId;
    private String deliverDay;
    private String payterm;
    private Date createDate;
    private Integer createBy;
    private String description;

    @Transient
    private List<PurchaseDetail> purchaseDetailList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public String getDeliverDay() {
        return deliverDay;
    }

    public void setDeliverDay(String deliverDay) {
        this.deliverDay = deliverDay;
    }

    public String getPayterm() {
        return payterm;
    }

    public void setPayterm(String payterm) {
        this.payterm = payterm;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PurchaseDetail> getPurchaseDetailList() {
        return purchaseDetailList;
    }

    public void setPurchaseDetailList(List<PurchaseDetail> purchaseDetailList) {
        this.purchaseDetailList = purchaseDetailList;
    }
}
