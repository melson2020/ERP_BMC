package com.melson.webserver.contract.entity;

import javax.persistence.*;

/**
 * 合同公司实体
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Entity
@Table(name = "contract_org")
public class ContractOrg {

    /**
     * 合同公司唯一自增id
     */
    private Integer id;
    /**
     * 合同公司展示用编号,避免自增id暴露签约公司数量
     */
    private String contractNo;
    /**
     * 合同id
     */
    private Integer contractId;
    /**
     * 公司关联类型:1=买方;2=收货单位;3=卖方确认;4=买方确认;
     */
    private String type;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 单位地址,收货方必填
     */
    private String address;
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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
