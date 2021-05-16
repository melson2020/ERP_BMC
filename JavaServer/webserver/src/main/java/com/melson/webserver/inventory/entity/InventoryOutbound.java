package com.melson.webserver.inventory.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 出库单实体
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@Entity
@Table(name = "inventory_outbound")
public class InventoryOutbound {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 出库单号
     */
    private String formNo;
    /**
     * 出库类型
     */
    private String type;
    /**
     * 客户
     */
    private String customerNo;
    /**
     * 描述
     */
    private String remark;
    /**
     * 操作时间
     */
    private Date createDate;
    /**
     * 操作人
     */
    private Integer createUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
