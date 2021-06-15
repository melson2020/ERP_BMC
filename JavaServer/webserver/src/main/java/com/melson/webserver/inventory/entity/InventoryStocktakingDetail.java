package com.melson.webserver.inventory.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 盘点明细实体
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
@Entity
@Table(name = "inventory_stocktaking_detail")
public class InventoryStocktakingDetail {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 盘点单号
     */
    private String formNo;
    /**
     * 物料号/产品编码
     */
    private String materialNo;
    /**
     * 名称
     */
    private String name;
    /**
     * 规格
     */
    private String specification;
    /**
     * 库存数量
     */
    private Integer storageCount;
    /**
     * 库存数量单位
     */
    private String storageCountUnit;
    /**
     * 盘点数量
     */
    private Integer tackingCount;
    /**
     * 批次号
     */
    private String batchNo;

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

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
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

    public Integer getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(Integer storageCount) {
        this.storageCount = storageCount;
    }

    public String getStorageCountUnit() {
        return storageCountUnit;
    }

    public void setStorageCountUnit(String storageCountUnit) {
        this.storageCountUnit = storageCountUnit;
    }

    public Integer getTackingCount() {
        return tackingCount;
    }

    public void setTackingCount(Integer tackingCount) {
        this.tackingCount = tackingCount;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
