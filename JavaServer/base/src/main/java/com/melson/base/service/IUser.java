package com.melson.base.service;

import com.melson.base.IService;
import com.melson.base.entity.Company;
import com.melson.base.entity.User;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/25
 */
public interface IUser extends IService<User> {
    User CreateUser(User user);
    User CreateAdminUserForCompany(Company company,String password);
    User SystemLogin(User user);
}
