package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "product_bom")      //产品和BOM关系表
public class ProductBom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productNo;       // 产品编码
    private Integer processId;      // 工序
    private String processNo;       // 工序代号
    private String processName;     // 工序名称
    private BigDecimal processCost; // 工序费用
    private String productName;     // 产品名称
    private String specification;   // 产品规格
    private String bomNo;           // BOM编码
    private String bomGenericId;    // Bom Generic ID, 用于存储时间戳
    private BigDecimal costPrice;   // 对应价格
    private Date expirationDate;    // 过期时间
    private String version;         // 版本信息
    private String description;     // 备注描述， 信息来BOM表的描述备注，工序，工艺，尺寸等等
    private String createBy;        // 创建人
    private Date createDate;        // 创建日期
    private String status;          // 状态属性， Y：Bom正常使用 ；  N： Bom 停用

    @Transient
    private List<Boms> materialVos;   //传BOM过来


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

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getProcessNo() {
        return processNo;
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo;
    }


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }



    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBomNo() {
        return bomNo;
    }

    public void setBomNo(String bomNo) {
        this.bomNo = bomNo;
    }

    public String getBomGenericId() {
        return bomGenericId;
    }

    public void setBomGenericId(String bomGenericId) {
        this.bomGenericId = bomGenericId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Boms> getMaterialVos() {
        return materialVos;
    }

    public void setMaterialVos(List<Boms> materialVos) {
        this.materialVos = materialVos;
    }

    public BigDecimal getProcessCost() {
        return processCost;
    }

    public void setProcessCost(BigDecimal processCost) {
        this.processCost = processCost;
    }
}
