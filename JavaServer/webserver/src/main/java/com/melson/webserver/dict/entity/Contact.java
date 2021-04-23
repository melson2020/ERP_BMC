package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name="contact")      //联系人表
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contactId;      //联系人编号 32位编码
    private String name;           //联系人名称
    private String phone;          //联系人电话
    private String description;    //联系人描述备注
    private String status;         //联系人状态  Y：有效；N：失效
    private Date createDate;       //联系人创建日期
    private String createBy;       //联系人创建日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
