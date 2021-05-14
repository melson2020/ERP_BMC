package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "product")      //产品表
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productNo;       // 产品编码，8位编码规则 例：P0000001
    private String name;            // 产品名称
    private String categoryId;      // 产品类别，8位编码规则 例：CAT00001
    private String specification;   // 产品规格
    private String unit;            // 产品单位
    private BigDecimal salesPrice;  // 销售价格
    private String remark;          // 备注信息
    private String storageCode;     // 产品存储仓库编号 //TODO:进销存端要和这里的仓库编码对应起来
    private String status;          // 产品是否可售： N：不可售；Y（或空）表示可售
    private String description;     // 产品备注描述
    private String createBy;        // 产品创建人
    private Date createDate;        // 产品创建时间


    @Transient
    private String category;
    @Transient
    private String storageName;
    @Transient
    private List<ProductBom> productBomList;


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

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
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

    public List<ProductBom> getProductBomList() {
        return productBomList;
    }

    public void setProductBomList(List<ProductBom> productBomList) {
        this.productBomList = productBomList;
    }
}
