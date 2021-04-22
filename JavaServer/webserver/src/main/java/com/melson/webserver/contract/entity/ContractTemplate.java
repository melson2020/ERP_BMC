package com.melson.webserver.contract.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/22
 */
@Entity
@Table(name = "contract_template")
public class ContractTemplate {
    /**
     * 合同模板类型
     */
    public static final String TEMPLATE_NORMAL = "normal";
    public static final String DETAILS = "detail";
    public static final String REMARKS = "remark";
    public static final String CONTENT = "content";
    public static final String DES = "description";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contractType;
    private String code;
    private String value;
    private String bindingField;

    public ContractTemplate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
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

    public String getBindingField() {
        return bindingField;
    }

    public void setBindingField(String bindingField) {
        this.bindingField = bindingField;
    }
}
