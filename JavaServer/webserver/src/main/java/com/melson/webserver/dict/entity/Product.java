package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "product")      //商品表
public class Product {
    public static final String PRODUCT_NO_CHAR="M";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productNo;       // 商品编码，8位编码规则 例：P0000001
    private String name;            // 商品名称
    private String categoryId;      // 商品类别，8位编码规则 例：CAT00001
    private String specification;   // 商品规格
    private String unit;            // 商品单位
    private Integer supplyId;       // 商品供应商
    private BigDecimal salesPrice;  // 销售价格
    private BigDecimal weight;      // 物料重量
    private String weightUnit;      // 物料重量单位
    private BigDecimal volume;      // 物料体积
    private String volumeUnit;      // 物料体积单位
    private String remark;          // 备注信息
    private String storageCode;     // 商品存储仓库编号 //TODO:进销存端要和这里的仓库编码对应起来
    private String status;          // 商品是否可售： N：不可售；Y（或空）表示可售
    private String description;     // 商品备注描述
    private Date expireDate;        // 商品过期时间
    private String createBy;        // 商品创建人
    private Date createDate;        // 商品创建时间


    @Transient
    private String category;
    @Transient
    private String storageName;
    @Transient
    private String supplyName;
    @Transient
    private List<ProductBom> productBomList;


    public static String getProductNoChar() {
        return PRODUCT_NO_CHAR;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public List<ProductBom> getProductBomList() {
        return productBomList;
    }

    public void setProductBomList(List<ProductBom> productBomList) {
        this.productBomList = productBomList;
    }
}
