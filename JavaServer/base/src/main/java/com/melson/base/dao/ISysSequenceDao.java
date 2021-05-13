package com.melson.base.dao;

import com.melson.base.entity.SysSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/11
 */
@Repository
public interface ISysSequenceDao extends JpaRepository<SysSequence,Integer> {
    SysSequence findByKey(String key);
}
