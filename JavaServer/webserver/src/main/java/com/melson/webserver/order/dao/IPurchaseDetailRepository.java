package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PurchaseDetail;
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
public interface IPurchaseDetailRepository extends JpaRepository<PurchaseDetail,Integer> {
    List<PurchaseDetail> findBySourceIdAndType(Integer sourceId,String type);

    List<PurchaseDetail> findByPurchasePlanNo(String planNo);

    @Modifying
    @Query(value = "update `purchase_detail` set `state`=?1 where purchasePlanNo=?2",nativeQuery = true)
    void UpdateStatus(String state, String planNo);

    @Modifying
    @Query(value = "delete from `purchase_detail` where purchasePlanNo =?1",nativeQuery = true)
    void deleteByPrNo(String planNo);

    List<PurchaseDetail> findByState(String state);
}
