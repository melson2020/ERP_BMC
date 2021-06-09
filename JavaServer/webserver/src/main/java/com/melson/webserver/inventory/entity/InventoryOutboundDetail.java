package com.melson.webserver.inventory.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 出库明细实体
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@Entity
@Table(name = "inventory_outbound_detail")
public class InventoryOutboundDetail {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 出库单号
     */
    private String formNo;
    /**
     * 物料号/产品编码
     */
    private String materialNo;

    private Integer materialId;
    /**
     * 名称
     */
    private String name;
    /**
     * 规格
     */
    private String specification;
    /**
     * 数量
     */
    private Integer count;

    /**
     * 数量单位
     */
    private String unit;

    /**
     * 批次号，如果为多批次出库 则有多条出库详细
     */
    private String batchNo;

    /**
     * 仓库码
     */

    private String storageCode;



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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }
}
