package com.melson.webserver.inventory.vo;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 入库单
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
public class InventoryInboundVo {

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
     * 供应商
     */
    private Integer supplyId;

    private Date createDate;

    private Integer createUser;

    /**
     * 入库明细
     */
    @Transient
    private List<InventoryInboundDetailVo> detailVoList;

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


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InventoryInboundDetailVo> getDetailVoList() {
        return detailVoList;
    }

    public void setDetailVoList(List<InventoryInboundDetailVo> detailVoList) {
        this.detailVoList = detailVoList;
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

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
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
}
