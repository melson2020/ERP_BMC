package com.melson.webserver.dict.vo;

import java.math.BigDecimal;

/**
 * Created by Messi on 2021/4/26
 */
public class ProductVo {
    private String productId;
    private String productName;
    private String specification;
    private String value;
    private String remark;
    private BigDecimal salesPrice;

    public ProductVo() {
    }

    public ProductVo(String productId, String productName, String specification, String value, String remark, BigDecimal salesPrice) {
        this.productId = productId;
        this.productName = productName;
        this.specification = specification;
        this.value = value;
        this.remark = remark;
        this.salesPrice = salesPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }
}
