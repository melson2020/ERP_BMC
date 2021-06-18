package com.melson.webserver.order.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/16
 */@Entity
@Table(name = "order_delivery_detail")
public class OrderDeliveryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private String name;
    private String specification;
    private Integer count;
    private String unit;
    private String productNo;

    private Integer orderDeliveryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public Integer getOrderDeliveryId() {
        return orderDeliveryId;
    }

    public void setOrderDeliveryId(Integer orderDeliveryId) {
        this.orderDeliveryId = orderDeliveryId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
}
