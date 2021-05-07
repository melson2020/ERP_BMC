package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.ProducePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IProducePlanRepository extends JpaRepository<ProducePlan,Integer> {
}
