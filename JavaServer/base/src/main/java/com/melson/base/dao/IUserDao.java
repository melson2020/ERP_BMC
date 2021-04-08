package com.melson.base.dao;

import com.melson.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/25
 */
@Repository
public interface IUserDao extends JpaRepository<User,String> {
    User findByLoginNameAndPassword(String userName,String password);
}
