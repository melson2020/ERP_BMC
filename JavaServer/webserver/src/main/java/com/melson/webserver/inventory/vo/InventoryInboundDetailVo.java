package com.melson.webserver.inventory.vo;

import java.math.BigDecimal;

/**
 * 入库明细
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
public class InventoryInboundDetailVo {

    /**
     * 物料号/产品编码
     */
    private String materialNo;
    /**
     * 名称
     */
    private String materialName;
    /**
     * 规格
     */
    private String specification;
    /**
     * 数量
     */
    private BigDecimal count;
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

    public InventoryInboundDetailVo() {

    }

    public InventoryInboundDetailVo(String materialNo, String materialName, String specification, BigDecimal count, BigDecimal latestPrice, String unit, String storageCode) {
        this.materialNo = materialNo;
        this.materialName = materialName;
        this.specification = specification;
        this.count = count;
        this.latestPrice = latestPrice;
        this.unit = unit;
        this.storageCode = storageCode;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
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

    public BigDecimal getCount() {
        return count;
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
