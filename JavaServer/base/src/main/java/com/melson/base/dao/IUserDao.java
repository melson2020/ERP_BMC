package com.melson.base.dao;

import com.melson.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/25
 */
@Repository
public interface IUserDao extends JpaRepository<User, String> {
    User findByLoginNameAndPassword(String userName, String password);

    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return
     */
    @Query
    User findById(Integer id);

    /**
     * 更新密码
     *
     * @param id  用户id
     * @param pwd md5加密后的新密码
     */
    @Modifying
    @Query(nativeQuery = true, value = "update user set password = :pwd where id = :id")
    void updatePwd(@Param("id") Integer id, @Param("pwd") String pwd);

    @Query(value = "SELECT * FROM `user` where `status`='Y'",nativeQuery = true)
    List<User> findByStatus();

    User findByUserNameAndLoginName(String userName, String loginName);

    @Modifying
    @Query(value = "update `user` set `status`=?1 where id=?2",nativeQuery = true)
    Integer UpdateUserStatus(String status, Integer id);
}
