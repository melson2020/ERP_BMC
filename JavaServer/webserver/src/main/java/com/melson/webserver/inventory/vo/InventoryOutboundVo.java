package com.melson.webserver.inventory.vo;

import java.util.List;

/**
 * 出库单
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
public class InventoryOutboundVo {

    /**
     * 出库单号
     */
    private String formNo;
    /**
     * 出库类型
     */
    private String type;
    /**
     * 客户
     */
    private String customerNo;
    /**
     * 描述
     */
    private String remark;
    /**
     * 出库明细
     */
    private List<InventoryOutboundDetailVo> detailVoList;

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
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

    public List<InventoryOutboundDetailVo> getDetailVoList() {
        return detailVoList;
    }

    public void setDetailVoList(List<InventoryOutboundDetailVo> detailVoList) {
        this.detailVoList = detailVoList;
    }
}
