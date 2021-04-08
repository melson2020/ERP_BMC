package com.melson.base.dao;

import com.melson.base.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/29
 */
@Repository
public interface IRoleDao extends JpaRepository<Role,String> {
    Role findByRoleNameAndCompanyId(String roleName,String companyId);
}
