package com.melson.webserver.inventory.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 入库明细实体
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
@Entity
@Table(name = "inventory_inbound_detail")
public class InventoryInboundDetail {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 入库单号
     */
    private String formNo;
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

    private Integer materialId;

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

    public void setCount(BigDecimal count) {
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

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }
}
