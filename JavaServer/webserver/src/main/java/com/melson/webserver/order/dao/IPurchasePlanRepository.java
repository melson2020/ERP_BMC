package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PurchasePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IPurchasePlanRepository extends JpaRepository<PurchasePlan,Integer> {
    List<PurchasePlan> findByCreateBy(Integer userId);

    PurchasePlan findByPlanNo(String planNo);

    @Modifying
    @Query(value = "update `purchase_plan` set `state`=?1 where id=?2",nativeQuery = true)
    Integer UpdatePurchaseStatus(String state, Integer id);

    @Modifying
    @Query(value = "delete from `purchase_plan` where id =?1",nativeQuery = true)
    Integer deletePrById(Integer id);

    List<PurchasePlan> findListByState(String state);

    @Modifying
    @Query(value = "update `purchase_plan` set `state`=?1 where planNo=?2",nativeQuery = true)
    void UpdateState(String state,String purchasePlanNo);
}
