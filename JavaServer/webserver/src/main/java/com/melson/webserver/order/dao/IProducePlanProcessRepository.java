package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.order.entity.ProducePlanProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/12
 */
@Repository
public interface IProducePlanProcessRepository extends JpaRepository<ProducePlanProcess,Integer> {
    List<ProducePlanProcess> findByPlanIdAndPlanDetailIdOrderByProcessIndex(Integer planId,Integer planDetailId);
    List<ProducePlanProcess> findByPlanId(Integer planId);
}
