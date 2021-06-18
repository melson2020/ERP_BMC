package com.melson.webserver.inventory.dto;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/17
 */
public class OutboundRequireDto {
    private Integer materialId;
    private String materialNo;
    private String name;
    private String specification;
    private String unit;
    private Integer count;
    //客户给料标志
    private String customerSupplyFlag;

    public OutboundRequireDto(Integer materialId, String materialNo, String name, String specification, String unit, Integer count, String customerSupplyFlag) {
        this.materialId = materialId;
        this.materialNo = materialNo;
        this.name = name;
        this.specification = specification;
        this.unit = unit;
        this.count = count;
        this.customerSupplyFlag = customerSupplyFlag;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCustomerSupplyFlag() {
        return customerSupplyFlag;
    }

    public void setCustomerSupplyFlag(String customerSupplyFlag) {
        this.customerSupplyFlag = customerSupplyFlag;
    }
}
