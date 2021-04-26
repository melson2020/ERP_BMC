package com.melson.webserver.dict.vo;

import java.math.BigDecimal;

/**
 * Created by Messi on 2021/4/26
 */
public class ProductVo {
    private Integer id;
    private String productName;
    private String value;
    private String remark;
    private BigDecimal salesPrice;

    public ProductVo() {
    }

    public ProductVo(Integer id, String productName, String value, String remark, BigDecimal salesPrice) {
        this.id = id;
        this.productName = productName;
        this.value = value;
        this.remark = remark;
        this.salesPrice = salesPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
