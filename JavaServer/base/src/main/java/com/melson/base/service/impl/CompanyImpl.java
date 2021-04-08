package com.melson.base.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.dao.ICompanyDao;
import com.melson.base.dao.IRoleDao;
import com.melson.base.entity.Company;
import com.melson.base.entity.Role;
import com.melson.base.entity.SystemConfig;
import com.melson.base.entity.User;
import com.melson.base.service.ICompany;
import com.melson.base.service.ISystemConfig;
import com.melson.base.service.IUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/21
 */
@Service
public class CompanyImpl extends AbstractService<Company> implements ICompany {
    private final ICompanyDao companyDao;
    private final IRoleDao roleDao;
    private final ISystemConfig systemConfigService;
    private final IUser userService;

    public CompanyImpl(ICompanyDao companyDao, IRoleDao roleDao, ISystemConfig systemConfigService, IUser userService) {
        this.companyDao = companyDao;
        this.roleDao = roleDao;
        this.systemConfigService = systemConfigService;
        this.userService = userService;
    }

    @Override
    public JpaRepository<Company, String> getRepository() {
        return companyDao;
    }

    @Override
    public boolean CheckPhone(String phoneNumber) {
        Company company = companyDao.findByPhoneNumber(phoneNumber);
        return company != null;
    }

    @Override
    @Transactional
    public Result RegisterCompany(Company company, String password) {
        Result result = new Result();
        //验证是否重复
        Company existCompany = companyDao.findByCompanyName(company.getCompanyName());
        if (existCompany != null) {
            result.setResultStatus(-1);
            result.setMessage("Company name has been registered");
            return result;
        }
        //注册公司并且生成默认的角色列表
        Company saved = CreateCompanyAndRoles(company);
        if (saved != null) {
            User adminUser = userService.CreateAdminUserForCompany(company, password);
            if (adminUser != null) {
                result.setResultStatus(1);
            } else {
                result.setResultStatus(-1);
                result.setMessage("create admin user failed");
            }
        } else {
            result.setResultStatus(-1);
            result.setMessage("register company failed");
        }
        return result;
    }

    //生成公司 返回公司admin roleId
    public Company CreateCompanyAndRoles(Company company) {
        SystemConfig config = systemConfigService.findFormCache("REGISTER_DELAY_DATE");
        //注册公司 自动生成公司admin 账户
        Date createDate = new Date();
        company.setCompanyCode(UUID.randomUUID().toString());
        company.setCreateDate(createDate);
        Calendar calendar = Calendar.getInstance();
        Integer unit = Integer.parseInt(config.getUnit());
        Integer value = Integer.parseInt(config.getValue());
        calendar.add(unit, value);
        Date expireDate = calendar.getTime();
        company.setExpireDate(expireDate);
        company.setStatus(0);
        Company saved = companyDao.save(company);
        if(saved!=null){
           List<Role> createdRoles=GenerateDefaultRoles(saved.getCompanyCode());
           if(createdRoles!=null&&createdRoles.size()>0){
               return company;
           }else {
               return null;
           }
        }else {
            return null;
        }
    }

    private List<Role> GenerateDefaultRoles(String companyId){
        SystemConfig config = systemConfigService.findFormCache("REGISTER_DEFAULT_ROLES");
        String[] roleNameList=config.getValue().split(config.getUnit());
        List<Role> createdList=new ArrayList<>();
        for (String name:roleNameList){
           Role role=new Role();
           role.setRoleId(UUID.randomUUID().toString());
           role.setRoleName(name);
           role.setCompanyId(companyId);
           createdList.add(role);
        }
       return roleDao.saveAll(createdList);
    }
}
