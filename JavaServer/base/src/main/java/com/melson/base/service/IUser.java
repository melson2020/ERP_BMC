package com.melson.base.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.base.entity.Company;
import com.melson.base.entity.User;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/25
 */
public interface IUser extends IService<User> {
    User CreateUser(User user);

    User CreateAdminUserForCompany(Company company, String password);

    User SystemLogin(User user);

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    User get(Integer id);

    /**
     * 更新密码
     *
     * @param id  用户id
     * @param pwd md5加密后的新密码
     */
    void updatePwd(Integer id, String pwd);

    List<User> GetEmployeeList(String userType);

    Result SaveAndUpdate(User user);

    User QueryEmployee(Integer id);

    Integer UpdateUserStatus(User user);


    User findByUserId(String userId);

}
