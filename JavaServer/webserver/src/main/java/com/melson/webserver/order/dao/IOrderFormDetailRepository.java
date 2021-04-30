package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.OrderFormDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/30
 */
@Repository
public interface IOrderFormDetailRepository extends JpaRepository<OrderFormDetail, Integer> {
}
