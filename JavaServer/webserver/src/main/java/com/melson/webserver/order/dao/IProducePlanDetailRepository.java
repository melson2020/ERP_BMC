package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.ProducePlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IProducePlanDetailRepository extends JpaRepository<ProducePlanDetail,Integer> {
    List<ProducePlanDetail> findByProducePlanId(Integer planId);
}
