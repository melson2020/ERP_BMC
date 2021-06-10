package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Entity
@Table(name = "picking_ticket_detail")
public class PickingTicketDetail {
    public static final String STATE_CREATE="CREATE";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 类型 标明领料类型 P 生产 D 代工 W委外
     */
    private String type;

    private Integer ticketId;

    private String materialNo;

    private String materialName;

    private String specification;

    private String remark;

    private BigDecimal count;

    private String countUnit;

    private String purchasePlanNo;

    private String createEmployeeNo;

    private Date createDate;

    //状态值  如;创建， 已下单，采购中，已入库，完成
    private String state;
    /**
     * 是否委外取料 Y/N
     */
    private String delegateFlag;

    private Integer productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
    }

    public String getPurchasePlanNo() {
        return purchasePlanNo;
    }

    public void setPurchasePlanNo(String purchasePlanNo) {
        this.purchasePlanNo = purchasePlanNo;
    }

    public String getCreateEmployeeNo() {
        return createEmployeeNo;
    }

    public void setCreateEmployeeNo(String createEmployeeNo) {
        this.createEmployeeNo = createEmployeeNo;
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

    public String getDelegateFlag() {
        return delegateFlag;
    }

    public void setDelegateFlag(String delegateFlag) {
        this.delegateFlag = delegateFlag;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
