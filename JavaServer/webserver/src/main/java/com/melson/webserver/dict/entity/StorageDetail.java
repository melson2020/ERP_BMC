package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Created by Messi on 2021/5/10
 */
@Entity
@Table(name="storage_detail")
public class StorageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String materialNo;         // 包括物料号，产品号等
    private String name;
    private String specification;
    private Integer count;
    private Integer supplyId;
    private BigDecimal lastestPrice;
    private String unit;
    private String feature;
    private String storageCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public BigDecimal getLastestPrice() {
        return lastestPrice;
    }

    public void setLastestPrice(BigDecimal lastestPrice) {
        this.lastestPrice = lastestPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
}
