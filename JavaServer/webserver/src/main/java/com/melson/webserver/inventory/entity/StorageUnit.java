package com.melson.webserver.inventory.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description 库存包装单位表 包括单位换算
 * @Date 2021/6/1
 */
@Entity
@Table(name = "storage_unit")
public class StorageUnit {
    /**
     * 唯一自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;

    private  Integer storageId;

    private String packageUnit;

    private Integer convertCount;

    private String convertUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public Integer getConvertCount() {
        return convertCount;
    }

    public void setConvertCount(Integer convertCount) {
        this.convertCount = convertCount;
    }

    public String getConvertUnit() {
        return convertUnit;
    }

    public void setConvertUnit(String convertUnit) {
        this.convertUnit = convertUnit;
    }
}
