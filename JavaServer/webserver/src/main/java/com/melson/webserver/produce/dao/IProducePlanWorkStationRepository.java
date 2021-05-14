package com.melson.webserver.produce.dao;

import com.melson.webserver.produce.entity.ProducePlanWorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/13
 */
@Repository
public interface IProducePlanWorkStationRepository extends JpaRepository<ProducePlanWorkStation,Integer> {
    List<ProducePlanWorkStation> findByPlanId(Integer planId);
}
