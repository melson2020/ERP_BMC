package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.OrderFormDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/30
 */
@Repository
public interface IOrderFormDetailRepository extends JpaRepository<OrderFormDetail, Integer> {
    @Query
    List<OrderFormDetail> findByOrderFormId(Integer formId);
}
