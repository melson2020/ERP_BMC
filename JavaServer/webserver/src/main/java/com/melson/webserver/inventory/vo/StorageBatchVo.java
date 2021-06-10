package com.melson.webserver.inventory.vo;

import javax.persistence.Transient;

/**
 * @Author Nelson
 * @Description 库存批次信息
 * @Date 2021/6/8
 */
public class StorageBatchVo {
    private Integer materialId;
    private Integer materialCount;
    private String materialUnit;
    private String batchNo;
    private Integer batchCount;
    private String batchUnit;
    private String storageCode;

    @Transient
    private Integer baseUnitCount;
    @Transient
    private String baseUnit;
    @Transient
    private Integer convertBaseCount;

    //拆包标记
    @Transient
    private Integer unPackageFlag;

    //拆出数量
    @Transient
    private Integer unPackageCount;

    public StorageBatchVo(){

    }

    public StorageBatchVo(Integer materialId, Integer materialCount, String materialUnit, String batchNo, Integer batchCount, String batchUnit, String storageCode) {
        this.materialId = materialId;
        this.materialCount = materialCount;
        this.materialUnit = materialUnit;
        this.batchNo = batchNo;
        this.batchCount = batchCount;
        this.batchUnit = batchUnit;
        this.storageCode = storageCode;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(Integer materialCount) {
        this.materialCount = materialCount;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(Integer batchCount) {
        this.batchCount = batchCount;
    }

    public String getBatchUnit() {
        return batchUnit;
    }

    public void setBatchUnit(String batchUnit) {
        this.batchUnit = batchUnit;
    }

    public Integer getBaseUnitCount() {
        return baseUnitCount;
    }

    public void setBaseUnitCount(Integer baseUnitCount) {
        this.baseUnitCount = baseUnitCount;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public Integer getConvertBaseCount() {
        return convertBaseCount;
    }

    public void setConvertBaseCount(Integer convertBaseCount) {
        this.convertBaseCount = convertBaseCount;
    }

    public Integer getUnPackageFlag() {
        return unPackageFlag;
    }

    public void setUnPackageFlag(Integer unPackageFlag) {
        this.unPackageFlag = unPackageFlag;
    }

    public Integer getUnPackageCount() {
        return unPackageCount;
    }

    public void setUnPackageCount(Integer unPackageCount) {
        this.unPackageCount = unPackageCount;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
}
