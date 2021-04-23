package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "boms")
public class Boms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                   //
    private String bomNo;                 // Bom编码，8位编码规则 例：B0000001
    private String processNo;             // 工序序号，对应produce_process表
    private String PartNo;                // 母件号 ， 非制造半成品的时候的物料通常母件号为空， 从基础物料表中选择
    private String chPartNo;              // 子件号    非制造半成品的时候的物料通常母件号为空， 从基础物料表中选择
    private BigDecimal chQty;             // 子件数量
    private BigDecimal lossRate;          // 子件折损率
    private String processStation;        // 工位
    private String manuafcturer;          // 生产商
    private BigDecimal materialCost;      // 物料成本  选择半成品时成本为基础物料成本+对应工序人工成本
    private BigDecimal humanCost;         // 人工成本 对应produce_process表，不同工序的人工成本不同
    private String materialCostStatus;    // 物料成本状态，Y：计算物料成本 ； N ：不计算物料成本，多指来料代加工，只赚人工费
    private String startBatchNo;          // 物料起始批次号
    private String endBatchNo;            // 物料截止批次号
    private Date expireDate;              // BOM有效期
    private String decription;            // BOM描述备注：工序，工艺，尺寸等等
    private String status;                // BOM状态值 Y：启用；N：停用
    private String vers;                  // BOM版本号
    private String createBy;              // BOM创建人
    private Date createDate;              // BOM创建日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBomNo() {
        return bomNo;
    }

    public void setBomNo(String bomNo) {
        this.bomNo = bomNo;
    }

    public String getProcessNo() {
        return processNo;
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo;
    }

    public String getPartNo() {
        return PartNo;
    }

    public void setPartNo(String partNo) {
        PartNo = partNo;
    }

    public String getChPartNo() {
        return chPartNo;
    }

    public void setChPartNo(String chPartNo) {
        this.chPartNo = chPartNo;
    }

    public BigDecimal getChQty() {
        return chQty;
    }

    public void setChQty(BigDecimal chQty) {
        this.chQty = chQty;
    }

    public BigDecimal getLossRate() {
        return lossRate;
    }

    public void setLossRate(BigDecimal lossRate) {
        this.lossRate = lossRate;
    }

    public String getProcessStation() {
        return processStation;
    }

    public void setProcessStation(String processStation) {
        this.processStation = processStation;
    }

    public String getManuafcturer() {
        return manuafcturer;
    }

    public void setManuafcturer(String manuafcturer) {
        this.manuafcturer = manuafcturer;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getHumanCost() {
        return humanCost;
    }

    public void setHumanCost(BigDecimal humanCost) {
        this.humanCost = humanCost;
    }

    public String getMaterialCostStatus() {
        return materialCostStatus;
    }

    public void setMaterialCostStatus(String materialCostStatus) {
        this.materialCostStatus = materialCostStatus;
    }

    public String getStartBatchNo() {
        return startBatchNo;
    }

    public void setStartBatchNo(String startBatchNo) {
        this.startBatchNo = startBatchNo;
    }

    public String getEndBatchNo() {
        return endBatchNo;
    }

    public void setEndBatchNo(String endBatchNo) {
        this.endBatchNo = endBatchNo;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVers() {
        return vers;
    }

    public void setVers(String vers) {
        this.vers = vers;
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
