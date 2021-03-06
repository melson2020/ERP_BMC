package com.melson.webserver.inventory.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 入库单实体
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
@Entity
@Table(name = "inventory_inbound")
public class InventoryInbound {
    public static final String TYPE_DELEGATE = "DELEGATE";
    public static final String TYPE_PURCHASE = "PURCHASE";
    public static final String TYPE_PRODUCE = "PRODUCE";
    public static final String TYPE_OTHERS = "OTHERS";
    public static final String TYPE_OEM = "OEM";

    /**
     * 编码前缀
     */
    public static final String CODE_PREFIX = "I";

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 入库单号
     */
    private String formNo;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 入库类型
     */
    private String type;
    /**
     * 来源单号
     */
    private String sourceNo;
    /**
     * 来源Id
     */
    private Integer sourceId;
    /**
     * 描述
     */
    private String remark;
    /**
     * 操作时间
     */
    private Date createDate;
    /**
     * 操作人
     */
    private Integer createUser;

    private Integer supplyId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }
}
