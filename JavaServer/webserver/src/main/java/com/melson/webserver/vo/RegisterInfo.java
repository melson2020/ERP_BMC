package com.melson.webserver.vo;

import com.melson.base.entity.Company;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/25
 */
public class RegisterInfo {
    private Company company;
    private String password;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
