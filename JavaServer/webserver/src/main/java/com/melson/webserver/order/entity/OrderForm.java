package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单定义实体
 *
 * @author wuhuan
 * @date 2021年04月27日
 */
@Entity
@Table(name = "order_form")
public class OrderForm {

    /**
     * 订单唯一自增id,用于其他表关联
     */
    private Integer id;
    /**
     * 订单展示用编号,避免自增id暴露订单数量
     */
    private String formNo;
    /**
     * 订单id
     */
    private Integer contractId;
    /**
     * 订单类型:1=自产;2=贸易;3=代工;4=委外;
     */
    private String type;
    /**
     * 订单状态:0=已作废;1=已下单;2=生产中;3=已出单;
     */
    private String state;
    /**
     * 描述
     */
    private String info;
    /**
     * 合同创建日期
     */
    private Date createDate;
    /**
     * 合同创建人,关联员工表
     */
    private Integer createUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
