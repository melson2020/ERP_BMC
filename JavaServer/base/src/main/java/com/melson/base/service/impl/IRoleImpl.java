package com.melson.base.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.dao.IRoleDao;
import com.melson.base.entity.Role;
import com.melson.base.service.IRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/5/25
 */
@Service
public class IRoleImpl extends AbstractService<Role> implements IRole {
    private final IRoleDao roleDao;

    public IRoleImpl(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public JpaRepository<Role, String> getRepository() {
        return roleDao;
    }


    @Override
    public List<Role> GetRoleList() {
        return roleDao.findAll();
    }
}
