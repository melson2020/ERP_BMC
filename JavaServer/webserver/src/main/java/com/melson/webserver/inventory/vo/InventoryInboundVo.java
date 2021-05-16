package com.melson.webserver.inventory.vo;

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
     * 供应商
     */
    private String customerNo;
    /**
     * 描述
     */
    private String remark;
    /**
     * 入库明细
     */
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

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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
}
