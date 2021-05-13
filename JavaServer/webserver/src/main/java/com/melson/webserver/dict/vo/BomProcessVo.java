package com.melson.webserver.dict.vo;

import java.math.BigDecimal;

/**
 * @Author Nelson
 * @Description BOM 工序信息实体 包含BOM ,Material,Produce Process
 * @Date 2021/5/12
 */
public class BomProcessVo {
    private Integer id;
    private String bomNo;
    private Integer processId;
    private String processNo;
    private String processName;
    private String PartNo;
    private String chPartNo;
    private String materialName;
    private BigDecimal materialCount;
    private String specification;
    private String index;
    private String delegateFlag;

    public BomProcessVo() {
    }

    public BomProcessVo(Integer id, String bomNo, Integer processId, String processNo, String processName, String partNo, String chPartNo, String materialName, BigDecimal materialCount, String specification, String index, String delegateFlag) {
        this.id = id;
        this.bomNo = bomNo;
        this.processId = processId;
        this.processNo = processNo;
        this.processName = processName;
        PartNo = partNo;
        this.chPartNo = chPartNo;
        this.materialName = materialName;
        this.materialCount = materialCount;
        this.specification = specification;
        this.index = index;
        this.delegateFlag = delegateFlag;
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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public BigDecimal getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(BigDecimal materialCount) {
        this.materialCount = materialCount;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getDelegateFlag() {
        return delegateFlag;
    }

    public void setDelegateFlag(String delegateFlag) {
        this.delegateFlag = delegateFlag;
    }
}
