package com.melson.webserver.dict.entity;

import javax.persistence.*;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "deliver_address")      //客户送货地址表
public class DeliverAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerNo;      // 客户编号
    private String contactId;       // 联系人编号
    private String deliverAddress;  // 送货地址
    private String phone;           // 联系电话
    private String tags;            // 标签信息：  例如，默认收货地址等
    private String description;     // 备注描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
