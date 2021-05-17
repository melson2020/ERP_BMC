package com.melson.base.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.base.entity.Company;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/21
 */
public interface ICompany extends IService<Company> {
    boolean CheckPhone(String phoneNumber);

    /**
     * 公司注册
     * @param company
     */
    Result RegisterCompany(Company company,String password);

    Company FindCompanyOne();
}
