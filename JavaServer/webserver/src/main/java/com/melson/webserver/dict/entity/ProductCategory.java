package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "product_category")      //产品类别表
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String categoryId;      // 产品类别，8位编码规则 例：CAT00001
    private String name;            // 类别名称
    private String description;     // 备注信息
    private String createBy;        // 创建人
    private Date createDate;        // 创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
