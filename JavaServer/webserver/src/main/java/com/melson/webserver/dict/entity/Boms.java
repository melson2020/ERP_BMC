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
    public static final String BOM_NO_CHAR="B";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                   //
    private String bomNo;                 // Bom编码，8位编码规则 例：B0000001
    private String bomGenericId;          // Bom Generic ID, 用于存储时间戳
    private Integer processId;            // 工序自增编号
    private String processNo;             // 工序序号，对应produce_process表
    private String partNo;                // 母件号 ， 非制造半成品的时候的物料通常母件号为空， 从基础物料表中选择
    private String partName;
    private String chPartNo;              // 子件号    非制造半成品的时候的物料通常母件号为空， 从基础物料表中选择
    private String chName;                // 子件名
    private String chBomStatus;           // 是否包含子物料BOM， Y：包含； N：不包含
    private String chBomNo;               // 子物料BOM 编码
    private String chBomGenericId;        // 子物料BOM 的识别符
    private String specification;         // 子件规格
    private BigDecimal salesPrice;        // 子件成本
    private String sIndex;                // 关系树
    private BigDecimal chQty;             // 子件数量
    private BigDecimal lossRate;          // 子件折损率
    private String processStation;        // 工位
    private Integer supplyId;             // 生产商
    private String supplyName;
    private String materialCostStatus;    // 物料成本状态，Y：计算物料成本 ； N ：不计算物料成本，多指来料代加工，只赚人工费
//    private BigDecimal materialCost;      // 物料成本  选择半成品时成本为基础物料成本+对应工序人工成本
//    private BigDecimal humanCost;         // 人工成本 对应produce_process表，不同工序的人工成本不同
//    private String startBatchNo;          // 物料起始批次号
//    private String endBatchNo;            // 物料截止批次号
//    private Date expireDate;              // BOM有效期
//    private String description;           // BOM描述备注：工序，工艺，尺寸等等
//    private String status;                // BOM状态值 Y：启用；N：停用
//    private String vers;                  // BOM版本号
//    private String createBy;              // BOM创建人
//    private Date createDate;              // BOM创建日期


    public static String getBomNoChar() {
        return BOM_NO_CHAR;
    }

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

    public String getBomGenericId() {
        return bomGenericId;
    }

    public void setBomGenericId(String bomGenericId) {
        this.bomGenericId = bomGenericId;
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

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getChPartNo() {
        return chPartNo;
    }

    public void setChPartNo(String chPartNo) {
        this.chPartNo = chPartNo;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getChBomStatus() {
        return chBomStatus;
    }

    public void setChBomStatus(String chBomStatus) {
        this.chBomStatus = chBomStatus;
    }

    public String getChBomNo() {
        return chBomNo;
    }

    public void setChBomNo(String chBomNo) {
        this.chBomNo = chBomNo;
    }

    public String getChBomGenericId() {
        return chBomGenericId;
    }

    public void setChBomGenericId(String chBomGenericId) {
        this.chBomGenericId = chBomGenericId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getsIndex() {
        return sIndex;
    }

    public void setsIndex(String sIndex) {
        this.sIndex = sIndex;
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

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getMaterialCostStatus() {
        return materialCostStatus;
    }

    public void setMaterialCostStatus(String materialCostStatus) {
        this.materialCostStatus = materialCostStatus;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }
}
