package com.melson.webserver.dict.vo;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Nelson
 * @Description 装载BOM 信息实体 bom right join material
 * @Date 2021/5/6
 */
public class BomVo {
    /**
     * bom id
     */
    private Integer id;
    private String bomNo;
    private String partNo;
    private String name;
    private String chPartNo;
    private String unit;
    private BigDecimal chQty;
    private String manufacturer;
    private String specification;

    @Transient
    private List<BomVo> childList;

    public BomVo() {

    }

    public BomVo(Integer id, String bomNo, String partNo, String name, String chPartNo, String unit, BigDecimal chQty, String manufacturer, String specification) {
        this.id = id;
        this.bomNo = bomNo;
        this.partNo = partNo;
        this.name = name;
        this.chPartNo = chPartNo;
        this.unit = unit;
        this.chQty = chQty;
        this.manufacturer = manufacturer;
        this.specification = specification;
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

    public String getChPartNo() {
        return chPartNo;
    }

    public void setChPartNo(String chPartNo) {
        this.chPartNo = chPartNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getChQty() {
        return chQty;
    }

    public void setChQty(BigDecimal chQty) {
        this.chQty = chQty;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<BomVo> getChildList() {
        return childList;
    }

    public void setChildList(List<BomVo> childList) {
        this.childList = childList;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
