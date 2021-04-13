package com.melson.base.dao;

import com.melson.base.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/29
 */
@Repository
public interface IUserRoleDao extends JpaRepository<UserRole,String> {
    List<UserRole> findByUserId(String userId);
}
