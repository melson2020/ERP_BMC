package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.ProduceWorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
@Repository
public interface IProduceWorkStationRepository extends JpaRepository<ProduceWorkStation,Integer> {
    List<ProduceWorkStation> findByProduceLineId(Integer lineId);
    List<ProduceWorkStation> findByProduceProcessId(Integer processId);
    void  deleteByProduceLineId(Integer id);
}
