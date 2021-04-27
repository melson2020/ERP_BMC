package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.OrderForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单Repository
 *
 * @author wuhuan
 * @date 2021年04月27日
 */
@Repository
public interface IOrderFormRepository extends JpaRepository<OrderForm, Integer> {

    /**
     * 根据合同id查询订单
     *
     * @param contractId
     * @return
     */
    @Query
    List<OrderForm> findByContractId(Integer contractId);
}
