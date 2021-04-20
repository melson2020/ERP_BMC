package com.melson.webserver.contract.entity;

import javax.persistence.*;

/**
 * 合同额外属性实体
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Entity
@Table(name = "contract_extend")
public class ContractExtend {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 合同id
     */
    private Integer contractId;
    /**
     * 属性别名
     */
    private String code;
    /**
     * 属性值
     */
    private String value;

    public ContractExtend() {

    }

    public ContractExtend(Integer contractId, String code, String value) {
        this.contractId = contractId;
        this.code = code;
        this.value = value;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
