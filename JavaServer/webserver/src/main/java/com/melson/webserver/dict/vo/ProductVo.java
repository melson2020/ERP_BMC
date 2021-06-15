package com.melson.webserver.dict.vo;

import java.math.BigDecimal;

/**
 * Created by Messi on 2021/4/26
 */
public class ProductVo {
    private String productNo;
    private String name;
    private String specification;
    private String categoryId;
    private String category;
    private Integer supplyId;
    private BigDecimal salesPrice;
    private String supplyName;
    private String bomNo;
    private String bomGenericId;
    private String alias;
    private Integer id;
    private String unit;
    private String remark;


    public ProductVo() {
    }

    public ProductVo(String productNo, String name, String specification, String categoryId, String category, Integer supplyId, BigDecimal salesPrice, String supplyName, String bomNo, String bomGenericId, String alias, Integer id, String unit, String remark) {
        this.productNo = productNo;
        this.name = name;
        this.specification = specification;
        this.categoryId = categoryId;
        this.category = category;
        this.supplyId = supplyId;
        this.salesPrice = salesPrice;
        this.supplyName = supplyName;
        this.bomNo = bomNo;
        this.bomGenericId = bomGenericId;
        this.alias = alias;
        this.id = id;
        this.unit = unit;
        this.remark = remark;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getBomNo() {
        return bomNo;
    }

    public void setBomNo(String bomNo) {
        this.bomNo = bomNo;
    }

    public String getBomGenericId() {
        return bomGenericId;
    }

    public void setBomGenericId(String bomGenericId) {
        this.bomGenericId = bomGenericId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
