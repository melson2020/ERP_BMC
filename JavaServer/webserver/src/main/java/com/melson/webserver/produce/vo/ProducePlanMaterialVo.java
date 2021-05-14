package com.melson.webserver.produce.vo;

import java.math.BigDecimal;

/**
 * @Author Nelson
 * @Description 生产计划流程物料号信息
 * @Date 2021/5/12
 */
public class ProducePlanMaterialVo {
    private String materialNo;
    private String materialName;
    private BigDecimal count;

    public ProducePlanMaterialVo() {
    }

    public ProducePlanMaterialVo(String materialNo, String materialName, BigDecimal count) {
        this.materialNo = materialNo;
        this.materialName = materialName;
        this.count = count;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
