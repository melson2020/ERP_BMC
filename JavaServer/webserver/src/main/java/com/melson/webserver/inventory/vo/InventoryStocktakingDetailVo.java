package com.melson.webserver.inventory.vo;

import java.math.BigDecimal;

/**
 * 盘点明细
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
public class InventoryStocktakingDetailVo {

    /**
     * 盘点单号
     */
    private String formNo;
    /**
     * 物料号/产品编码
     */
    private String materialNo;
    /**
     * 名称
     */
    private String name;
    /**
     * 规格
     */
    private String specification;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 最新价格
     */
    private BigDecimal latestPrice;
    /**
     * 数量单位
     */
    private String unit;
    /**
     * 货位号
     */
    private String storageCode;

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
}
