package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Entity
@Table(name = "delegate_detail")
public class DelegateDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer delegateTicketId;
    private String objectName;
    private String remark;
    private String specification;
    private BigDecimal count;
    private String countUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDelegateTicketId() {
        return delegateTicketId;
    }

    public void setDelegateTicketId(Integer delegateTicketId) {
        this.delegateTicketId = delegateTicketId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
    }
}
