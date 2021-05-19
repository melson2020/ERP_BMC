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
    OrderForm findByContractId(Integer contractId);

    @Query
    List<OrderForm> findByState(String state);

    List<OrderForm> findByStateOrState(String state1,String state2);

    /**
     * 查询所有未完成 未作废的各 state 的数量
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT state, count(id) as count from order_form o WHERE o.state<>'0' and o.state<>'5' GROUP BY o.state;")
    List<Object[]> getAllStatueCount();

    /**
     * 查询所有未作废的各 produceType 的数量
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT produceType, count(id) as count from order_form o WHERE o.state >1 and o.createDate BETWEEN ?1 and ?2  GROUP BY o.produceType;")
    List<Object[]> getAllProduceTypeCount(String dateBegin,String dateEnd);
}
