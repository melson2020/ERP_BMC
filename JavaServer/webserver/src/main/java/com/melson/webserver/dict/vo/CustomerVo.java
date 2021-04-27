package com.melson.webserver.dict.vo;

/**
 * Created by Messi on 2021/4/26
 */
public class CustomerVo {
    private String customerNo;
    private String name;

    public CustomerVo() {
    }

    public CustomerVo(String customerNo, String name) {
        this.customerNo = customerNo;
        this.name = name;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
