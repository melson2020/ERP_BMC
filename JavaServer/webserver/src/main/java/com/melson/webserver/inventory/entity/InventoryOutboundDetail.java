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
     * 物料号
     */
    private String materialNo;
    /**
     * 物料ID
     */
    private Integer materialId;
    /**
     * 出库单号
     */
    private String formNo;
    /**
     * 名称
     */
    private String name;
    /**
     * 规格
     */
    private String specification;
    /**
     * 应出数量
     */
    private Integer count;
    /**
     * 应出数量单位
     */
    private String unit;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 出库数量
     */
    private Integer outCount;

    /**
     * 出库数量单位
     */
    private String outCountUnit;

    /**
     * 拆包数量 如拆1包
     */
    private Integer unPackageCount;

    /**
     * 拆包数量单位
     */
    private String unPackageCountUnit;

    /**
     * 拆包取出数量 如拆1包 取走10支
     */
    private Integer unPackagePickCount;
    /**
     * 货位号
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

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public String getOutCountUnit() {
        return outCountUnit;
    }

    public void setOutCountUnit(String outCountUnit) {
        this.outCountUnit = outCountUnit;
    }

    public Integer getUnPackageCount() {
        return unPackageCount;
    }

    public void setUnPackageCount(Integer unPackageCount) {
        this.unPackageCount = unPackageCount;
    }

    public String getUnPackageCountUnit() {
        return unPackageCountUnit;
    }

    public void setUnPackageCountUnit(String unPackageCountUnit) {
        this.unPackageCountUnit = unPackageCountUnit;
    }

    public Integer getUnPackagePickCount() {
        return unPackagePickCount;
    }

    public void setUnPackagePickCount(Integer unPackagePickCount) {
        this.unPackagePickCount = unPackagePickCount;
    }
}
