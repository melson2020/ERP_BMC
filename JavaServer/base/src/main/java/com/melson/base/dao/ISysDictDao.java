package com.melson.base.dao;

import com.melson.base.entity.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统字典Repository
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
@Repository
public interface ISysDictDao extends JpaRepository<SysDict, Integer> {

    /**
     * 根据状态查询数据字典
     *
     * @param state 状态
     * @return
     */
    @Query
    List<SysDict> findByState(String state);
}
