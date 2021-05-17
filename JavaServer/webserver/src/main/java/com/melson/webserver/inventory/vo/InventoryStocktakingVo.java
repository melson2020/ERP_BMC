package com.melson.webserver.inventory.vo;

import java.util.List;

/**
 * 盘点单
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
public class InventoryStocktakingVo {

    /**
     * 盘点单号
     */
    private String formNo;
    /**
     * 盘点类型
     */
    private String type;
    /**
     * 盘点内容
     */
    private String info;
    /**
     * 描述
     */
    private String remark;
    /**
     * 盘点明细
     */
    private List<InventoryStocktakingDetailVo> detailVoList;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InventoryStocktakingDetailVo> getDetailVoList() {
        return detailVoList;
    }

    public void setDetailVoList(List<InventoryStocktakingDetailVo> detailVoList) {
        this.detailVoList = detailVoList;
    }
}
