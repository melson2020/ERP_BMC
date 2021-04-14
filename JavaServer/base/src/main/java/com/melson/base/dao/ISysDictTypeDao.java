package com.melson.base.dao;

import com.melson.base.entity.SysDictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统字典类型Repository
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
@Repository
public interface ISysDictTypeDao extends JpaRepository<SysDictType, Integer> {

    /**
     * 根据状态查询数据字典类型
     *
     * @param state 状态
     * @return
     */
    @Query
    List<SysDictType> findByState(String state);
}
