package com.melson.webserver.dict.vo;

import javax.persistence.*;

/**
 * Created by Messi on 2021/4/23
 */
public class DeliverAddress {
    private Integer id;
    private String customerNo;      // 客户编号
    private String contactName;     // 联系人名字   ，  此字段为临时添加ID对应，将来会扩展
    private String deliverAddress;  // 送货地址
    private String phone;           // 联系电话
    private String tags;            // 标签信息：  例如，默认收货地址等
    private String description;     // 备注描述


    public DeliverAddress() {
    }

    public DeliverAddress(Integer id, String customerNo, String contactName, String deliverAddress, String phone, String tags, String description) {
        this.id = id;
        this.customerNo = customerNo;
        this.contactName = contactName;
        this.deliverAddress = deliverAddress;
        this.phone = phone;
        this.tags = tags;
        this.description = description;
    }

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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
