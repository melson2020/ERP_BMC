package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "material")      //基础物料表
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String partNo;          // 物料编码，8位编码规则 例：M0000001
    private String name;            // 物料名称
    private String unit;            // 物料单位
    private String storerageCode;   // 物料存储仓库， //TODO:进销存端要和这里的仓库编码对应起来
    private String manufacturer;    // 物料生产厂家
    private String specification;   // 物料规格
    private BigDecimal weight;      // 物料重量
    private String weightUnit;      // 物料重量单位
    private BigDecimal volume;      // 物料体积
    private String volumeUnit;      // 物料体积单位
    private String semiGoods;       // 物料是否半成品： Y：半成品；N（或空）表示非半成品
    private BigDecimal cost;        // 物料价格
    private String status;          // 物料状态：  N：不可用；Y（或空）表示可用
    private Date expireDate;        // 物料过期时间
    private String description;     // 物料备注描述
    private String createBy;        // 物料创建人
    private Date createDate;        // 物料创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStorerageCode() {
        return storerageCode;
    }

    public void setStorerageCode(String storerageCode) {
        this.storerageCode = storerageCode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public String getSemiGoods() {
        return semiGoods;
    }

    public void setSemiGoods(String semiGoods) {
        this.semiGoods = semiGoods;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
