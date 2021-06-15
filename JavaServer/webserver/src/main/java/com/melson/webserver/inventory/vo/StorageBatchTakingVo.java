package com.melson.webserver.inventory.vo;

/**
 * @Author Nelson
 * @Description 盘点时 库存批次信息载体
 * @Date 2021/6/15
 */
public class StorageBatchTakingVo {
    private Integer materialId;
    private String name;
    private String batchNo;
    private Integer count;
    private String countUnit;

    public StorageBatchTakingVo() {
    }

    public StorageBatchTakingVo(Integer materialId, String name, String batchNo, Integer count, String countUnit) {
        this.materialId = materialId;
        this.name = name;
        this.batchNo = batchNo;
        this.count = count;
        this.countUnit = countUnit;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
    }
}
