package com.melson.webserver.contract.vo;

import java.util.Date;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/28
 */
public class ContractShowVo {
    private Integer id;
    private String orderTicketNo;
    private String contractNo;
    private String customerName;
    private Date createDate;
    private String createEmployee;
    private String type;

    public ContractShowVo(){

    }

    public ContractShowVo(Integer id, String orderTicketNo, String contractNo, String customerName, Date createDate, String createEmployee, String type) {
        this.id = id;
        this.orderTicketNo = orderTicketNo;
        this.contractNo = contractNo;
        this.customerName = customerName;
        this.createDate = createDate;
        this.createEmployee = createEmployee;
        this.type = type;
    }

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateEmployee() {
        return createEmployee;
    }

    public void setCreateEmployee(String createEmployee) {
        this.createEmployee = createEmployee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderTicketNo() {
        return orderTicketNo;
    }

    public void setOrderTicketNo(String orderTicketNo) {
        this.orderTicketNo = orderTicketNo;
    }
}
