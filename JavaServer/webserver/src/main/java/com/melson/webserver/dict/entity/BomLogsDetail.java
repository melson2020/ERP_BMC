package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Messi on 2021/5/31
 */
@Entity
@Table(name = "bom_logs_detail")
public class BomLogsDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer bomLogId;
    private Integer bomId;
    private String bomNo;
    private String bomGenericId;
    private String processNo;
    private String partNo;
    private String chPartNo;
    private String chBomStatus;
    private String chBomNo;
    private String chBomGenericId;
    private String specification;
    private BigDecimal chQty;
    private BigDecimal lossRate;
    private String processStation;
    private Integer supplyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBomLogId() {
        return bomLogId;
    }

    public void setBomLogId(Integer bomLogId) {
        this.bomLogId = bomLogId;
    }

    public Integer getBomId() {
        return bomId;
    }

    public void setBomId(Integer bomId) {
        this.bomId = bomId;
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

    public String getChPartNo() {
        return chPartNo;
    }

    public void setChPartNo(String chPartNo) {
        this.chPartNo = chPartNo;
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
}
