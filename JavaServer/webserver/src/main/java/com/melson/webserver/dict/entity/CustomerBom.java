package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name="customer_bom")      //客户关联BOM表
public class CustomerBom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerNo;      // 客户编号
    private String productId;       // 产品编号
    private String bomNo;           // BOM编号
    private BigDecimal salesPrice;  // 销售价格
    private String remark;          // 备注信息
    private String description;     // 描述信息 ， 信息来BOM表的描述备注，工序，工艺，尺寸等等

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBomNo() {
        return bomNo;
    }

    public void setBomNo(String bomNo) {
        this.bomNo = bomNo;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
