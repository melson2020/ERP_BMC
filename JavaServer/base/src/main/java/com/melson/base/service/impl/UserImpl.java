package com.melson.base.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.dao.IRoleDao;
import com.melson.base.dao.IUserDao;
import com.melson.base.dao.IUserRoleDao;
import com.melson.base.entity.Company;
import com.melson.base.entity.User;
import com.melson.base.entity.UserRole;
import com.melson.base.service.IUser;
import com.melson.base.utils.MD5Util;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/25
 */
@Service
public class UserImpl extends AbstractService<User> implements IUser {
    private final IUserDao userDao;
    private final IUserRoleDao userRoleDao;
    private final IRoleDao roleDao;

    public UserImpl(IUserDao userDao, IUserRoleDao userRoleDao, IRoleDao roleDao) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.roleDao = roleDao;
    }

    @Override
    public JpaRepository<User, String> getRepository() {
        return userDao;
    }

    public User CreateUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setCreateDate(new Date());
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User CreateAdminUserForCompany(Company company, String password) {
        User user = new User();
        user.setUserName(company.getCompanyName() + "管理用户");
        user.setDepartment("admin");
        user.setUserType("admin");
        user.setSubscriptionId("");
        user.setCompanyCode(company.getCompanyCode());
        user.setLoginName(company.getPhoneNumber());
        user.setPassword(MD5Util.string2MD5(password));
        user.setUserId(UUID.randomUUID().toString());
        user.setCreateDate(new Date());
        return userDao.save(user);
    }

    public UserRole CreateUserRole(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        return userRoleDao.save(userRole);
    }

    @Override
    public User SystemLogin(User user) {
        String md5Pass = MD5Util.string2MD5(user.getPassword());
        User existUser = userDao.findByLoginNameAndPassword(user.getLoginName(), md5Pass);
        if (existUser == null) {
            return null;
        }

        return null;
    }
}
