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
     * 编码前缀
     */
    public static final String CODE_PREFIX = "STO";

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 盘点单号
     */
    private String formNo;
    /**
     * 盘点人员ID
     */
    private Integer userId;
    /**
     * 盘点人员名称
     */
    private String userName;
    /**
     * 盘点仓库编码
     */
    private String storageCode;
    /**
     * 盘点仓库名称
     */
    private String storageAreaName;
    /**
     * 盘点单状态
     */
    private String state;
    /**
     * 描述
     */
    private String remark;
    private Date createDate;



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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageAreaName() {
        return storageAreaName;
    }

    public void setStorageAreaName(String storageAreaName) {
        this.storageAreaName = storageAreaName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
