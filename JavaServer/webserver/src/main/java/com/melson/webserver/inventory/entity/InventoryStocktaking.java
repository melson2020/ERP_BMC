package com.melson.webserver.inventory.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 盘点单实体
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
@Entity
@Table(name = "inventory_stocktaking")
public class InventoryStocktaking {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 盘点单号
     */
    private String formNo;
    /**
     * 盘点类型
     */
    private String type;
    /**
     * 盘点内容
     */
    private String info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
