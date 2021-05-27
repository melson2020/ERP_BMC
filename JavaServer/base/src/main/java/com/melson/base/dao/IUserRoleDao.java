package com.melson.base.dao;

import com.melson.base.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Modifying
    @Query(value = "DELETE from `user_role` WHERE userId=?1",nativeQuery = true)
    void DeleteOlderRoles(String userId);

    @Query(value = "SELECT ur.id,ur.roleId,ur.userId,ur.description,ro.roleName FROM `user_role` ur LEFT JOIN role ro on ro.id=ur.roleId WHERE ur.userId=?1",nativeQuery = true)
    List<UserRole> findByUserIdContainRoleName(String userId);
}
