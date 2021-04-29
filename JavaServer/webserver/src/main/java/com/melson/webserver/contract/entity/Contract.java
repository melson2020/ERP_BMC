package com.melson.webserver.contract.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 合同定义实体
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Entity
@Table(name = "contract")
public class Contract {

    /**
     * 合同类型:1=意向合同
     */
    public static final String TYPE_INTENTION = "1";
    /**
     * 合同类型:2=正式合同
     */
    public static final String TYPE_FORMAL = "2";

    /**
     * 合同状态:0=已作废;1=待处理;2=进行中;3=已完成;
     */
    public static final String STATE_NEW = "1";
    public static final String STATE_PROCESSING = "2";


    /**
     * 合同唯一自增id,用于其他表关联
     */
    private Integer id;
    /**
     * 合同展示用编号,避免自增id暴露合同数量
     */
    private String contractNo;
    /**
     * 合同创建日期
     */
    private Date createDate;
    /**
     * 合同创建人,关联员工表
     */
    private Integer createUser;
    /**
     * 合同类型:1=意向合同;2=正式合同;
     */
    private String type;
    /**
     * 合同状态:0=已作废;1=待处理;2=进行中;3=已完成;
     */
    private String state;
    /**
     * 客户订单号码
     */
    private String orderTicketNo;
    /**
     * 运输方式,关联字典表
     */
    private String deliverType;
    /**
     * 付款时间
     */
    private Date payDate;
    /**
     * 付款方式,关联字典表
     */
    private String payWay;
    /**
     * 付款货币,关联字典表,页面默认人民币
     */
    private String currency;
    /**
     * 交货日期
     */
    private String deliverDate;
    /**
     * 联系人,关联员工表
     */
    private String contactUser;
    /**
     * 质量保证
     */
    private String quality;
    /**
     * 来源合同id,有值标识从意向合同转过来的,用于合同溯源
     */
    private Integer sourceId;
    /**
     * 转正时间
     */
    private Date formalDate;
     private String taxRate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public String getOrderTicketNo() {
        return orderTicketNo;
    }

    public void setOrderTicketNo(String orderTicketNo) {
        this.orderTicketNo = orderTicketNo;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Date getFormalDate() {
        return formalDate;
    }

    public void setFormalDate(Date formalDate) {
        this.formalDate = formalDate;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }
}
