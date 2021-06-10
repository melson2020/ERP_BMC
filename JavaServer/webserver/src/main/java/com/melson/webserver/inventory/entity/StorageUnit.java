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

    private String packageUnit;

    private Integer convertCount;

    private String convertUnit;

    private String baseUnit;

    private Integer baseUnitConvertCount;

    private Integer level;

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

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public Integer getBaseUnitConvertCount() {
        return baseUnitConvertCount;
    }

    public void setBaseUnitConvertCount(Integer baseUnitConvertCount) {
        this.baseUnitConvertCount = baseUnitConvertCount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
