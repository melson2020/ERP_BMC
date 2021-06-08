package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2021/6/7
 */
@Repository
public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder,String> {

    @Query(nativeQuery = true, value = "SELECT po.id,po.poNo,po.state,po.supplyId,po.deliverDay,po.payterm,po.createDate,po.createBy,po.description ,su.`name` as supplyName ,us.userName as createName FROM `purchase_order` po left JOIN supply su on po.supplyId=su.id LEFT JOIN `user` us on po.createBy=us.id where po.state=?1")
    List<Object[]> findByStateWithName(String state);

    PurchaseOrder findByPoNo(String poNo);

    @Query(nativeQuery = true, value = "SELECT po.id,po.poNo,po.state,po.supplyId,po.deliverDay,po.payterm,po.createDate,po.createBy,po.description ,su.`name` as supplyName ,us.userName as createName FROM `purchase_order` po left JOIN supply su on po.supplyId=su.id LEFT JOIN `user` us on po.createBy=us.id where po.poNo=?1")
    List<Object[]> findByPoNoWithSupplyName(String poNo);

    @Query(nativeQuery = true, value = "SELECT po.id,po.poNo,po.state,po.supplyId,po.deliverDay,po.payterm,po.createDate,po.createBy,po.description ,su.`name` as supplyName ,us.userName as createName FROM `purchase_order` po left JOIN supply su on po.supplyId=su.id LEFT JOIN `user` us on po.createBy=us.id")
    List<Object[]> findAllWithCreate();
}
