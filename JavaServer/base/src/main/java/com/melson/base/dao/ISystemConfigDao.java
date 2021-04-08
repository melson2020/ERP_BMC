package com.melson.base.dao;

import com.melson.base.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/22
 */
@Repository
public interface ISystemConfigDao extends JpaRepository<SystemConfig,String> {
}
