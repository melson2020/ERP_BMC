package com.melson.webserver.contract.entity;

import javax.persistence.*;

/**
 * 合同货物实体
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Entity
@Table(name = "contract_stock")
public class ContractStock {

    /**
     * 合同货物清单唯一自增id
     */
    private Integer id;
    /**
     * 合同id
     */
    private Integer contractId;
    /**
     * 货物id
     */
    private Integer productId;
    /**
     * 货物名称
     */
    private String productName;
    /**
     * 属性id
     */
    private Integer propId;
    /**
     * 属性名称
     */
    private String propName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数量,不确定是否会有特殊需求要半件,直接使用字符串
     */
    private String number;
    /**
     * 含税单价,可能为小数,直接使用字符串
     */
    private String taxPrice;
    /**
     * 含税总价,可能为小数,直接使用字符
     */
    private String taxTotalPrice;
    /**
     * 联系人
     */
    private Integer contactUser;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 税号
     */
    private String taxNo;
    /**
     * 账号
     */
    private String account;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPropId() {
        return propId;
    }

    public void setPropId(Integer propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getTaxTotalPrice() {
        return taxTotalPrice;
    }

    public void setTaxTotalPrice(String taxTotalPrice) {
        this.taxTotalPrice = taxTotalPrice;
    }

    public Integer getContactUser() {
        return contactUser;
    }

    public void setContactUser(Integer contactUser) {
        this.contactUser = contactUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}