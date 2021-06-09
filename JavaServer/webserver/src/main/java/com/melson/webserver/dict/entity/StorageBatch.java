package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Messi on 2021/5/10
 */
@Entity
@Table(name="storage_batch")
public class StorageBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer materialId;
    private String name;

    private String specification;
    private String batchNo;
    private Integer supplyId;
    private Integer count;
    private String countUnit;
    private String storageInCode;
    //入库时的类型  和入库单类型一致
    private String batchType;
    private Integer finished;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
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

    public String getStorageInCode() {
        return storageInCode;
    }

    public void setStorageInCode(String storageInCode) {
        this.storageInCode = storageInCode;
    }


    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

}
