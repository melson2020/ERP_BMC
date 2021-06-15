package com.melson.webserver.order.vo;

import java.math.BigDecimal;

/**
 * @Author Messi He
 * @Description
 * @Date 2021/06/11
 */
public class DashBoardItemVo {
    private String id;
    private String itemName;
    private BigDecimal itemValue;
    private String percent;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemValue() {
        return itemValue;
    }

    public void setItemValue(BigDecimal itemValue) {
        this.itemValue = itemValue;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
