package com.melson.base.dao;

import com.melson.base.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/29
 */
@Repository
public interface IRoleMenuDao extends JpaRepository<RoleMenu,String> {
}
