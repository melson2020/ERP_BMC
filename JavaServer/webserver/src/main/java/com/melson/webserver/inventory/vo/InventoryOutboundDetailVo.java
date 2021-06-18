package com.melson.webserver.inventory.vo;

import java.math.BigDecimal;

/**
 * 出库明细
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
public class InventoryOutboundDetailVo {

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
     * 数量
     */
    private Integer count;
    /**
     * 数量单位
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
     * 拆包数量 如拆5包
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
    /**
     * 物料ID
     */
    private Integer materialId;

    /**
     * 库存描述 如 1箱 6包 32支
     */
    private String storageDes;

    /**
     * 库存数量 已换算成基础单位
     */
    private Integer storageCount;


    /**
     * 库存是否充足 1充足
     */
    private Integer storageFlag;

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

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getStorageDes() {
        return storageDes;
    }

    public void setStorageDes(String storageDes) {
        this.storageDes = storageDes;
    }

    public Integer getStorageFlag() {
        return storageFlag;
    }

    public void setStorageFlag(Integer storageFlag) {
        this.storageFlag = storageFlag;
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

    public Integer getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(Integer storageCount) {
        this.storageCount = storageCount;
    }
}
