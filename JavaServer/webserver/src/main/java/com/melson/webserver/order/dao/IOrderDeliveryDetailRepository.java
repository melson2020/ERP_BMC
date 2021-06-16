package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.OrderDeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/16
 */
@Repository
public interface IOrderDeliveryDetailRepository extends JpaRepository<OrderDeliveryDetail,Integer> {
    List<OrderDeliveryDetail> findByOrderDeliveryId(Integer id);
}
