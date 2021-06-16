package com.melson.webserver.order.dao;


import com.melson.webserver.order.entity.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/16
 */
@Repository
public interface IOrderDeliveryRepository extends JpaRepository<OrderDelivery,Integer> {
    OrderDelivery findByOrderFormNo(String formNo);
    List<OrderDelivery> findByState(String state);
}
