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
     * 盘点人员ID
     */
    private Integer userId;
    /**
     * 盘点人员名称
     */
    private String userName;
    /**
     * 盘点仓库编码
     */
    private String storageCode;
    /**
     * 盘点仓库名称
     */
    private String storageAreaName;
    /**
     * 盘点单状态
     */
    private String state;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageAreaName() {
        return storageAreaName;
    }

    public void setStorageAreaName(String storageAreaName) {
        this.storageAreaName = storageAreaName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
