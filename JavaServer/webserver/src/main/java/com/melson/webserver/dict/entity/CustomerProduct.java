package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name="customer_product")      //客户关联BOM表
public class CustomerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerNo;      // 客户编号
    private String productNo;       // 产品编号
    private BigDecimal salesPrice;  // 销售价格
    private String remark;     // 描述信息 ， 信息来BOM表的描述备注，工序，工艺，尺寸等等

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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
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
}
