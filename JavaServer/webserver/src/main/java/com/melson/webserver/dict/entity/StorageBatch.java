package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Messi on 2021/5/10
 */
@Entity
@Table(name="storage_batch")
public class StorageBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String materialNo;
    private String name;
    private String bomNo;
    private String specification;
    private String batchNo;
    private String manufacturer;
    private Integer count;
    private String countUnit;
    private String storageInCode;
    private BigDecimal totalPrice;
    private String taxRate;
    private BigDecimal price;         // 含税价
    private BigDecimal tePrice;       // 不含税价
    private BigDecimal tax;           // 税金
    private String priceUnit;         // 金额单位；
    private String batchType;         // 批次类型 CIN：买入入库 ； AIN：盘点入库 ； PIN:成品入库； SIN：半成品入库
    private Integer finished;         // 用于计算是否这批批次的用量变动

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

    public String getBomNo() {
        return bomNo;
    }

    public void setBomNo(String bomNo) {
        this.bomNo = bomNo;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
    }

    public String getStorageInCode() {
        return storageInCode;
    }

    public void setStorageInCode(String storageInCode) {
        this.storageInCode = storageInCode;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTePrice() {
        return tePrice;
    }

    public void setTePrice(BigDecimal tePrice) {
        this.tePrice = tePrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }
}
