package com.melson.webserver.dict.vo;

import java.util.Date;

/**
 * Created by Messi on 2021/5/7
 */
public class CustomerContactVo {
    private Integer id;
    private String contactName;   //联系人名字   ，  此字段为临时添加ID对应，将来会扩展
    private String phone;           // 联系人电话
    private String customerNo;    // 客户编号
    private String name;           //客户名称
    private String deliverAddress;  // 送货地址
    private String status;         //状态定义    Y: 可用状态； N：不可用

    public CustomerContactVo() {
    }

    public CustomerContactVo(Integer id, String contactName, String phone, String customerNo, String name, String deliverAddress, String status) {
        this.id = id;
        this.contactName = contactName;
        this.phone = phone;
        this.customerNo = customerNo;
        this.name = name;
        this.deliverAddress = deliverAddress;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
