package com.melson.webserver.order.entity;

import com.melson.webserver.dict.entity.Supply;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal amount;
    private String formNo;
    private Date deliverDate;

    @Transient
    private List<PurchaseDetail> purchaseDetailList;
    @Transient
    private String supplyName;
    @Transient
    private String createName;
    @Transient
    private Supply supply;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public List<PurchaseDetail> getPurchaseDetailList() {
        return purchaseDetailList;
    }

    public void setPurchaseDetailList(List<PurchaseDetail> purchaseDetailList) {
        this.purchaseDetailList = purchaseDetailList;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }
}
