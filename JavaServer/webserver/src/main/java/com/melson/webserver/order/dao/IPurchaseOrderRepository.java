package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/6/7
 */
@Repository
public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder,Integer> {

    @Query(nativeQuery = true, value = "SELECT po.id,po.poNo,po.state,po.supplyId,po.deliverDay,po.payterm,po.createDate,po.createBy,po.description ,su.`name` as supplyName ,us.userName as createName,po.amount FROM `purchase_order` po left JOIN supply su on po.supplyId=su.id LEFT JOIN `user` us on po.createBy=us.id where po.state=?1")
    List<Object[]> findByStateWithName(String state);

    PurchaseOrder findByPoNo(String poNo);

    @Query(nativeQuery = true, value = "SELECT po.id,po.poNo,po.state,po.supplyId,po.deliverDay,po.payterm,po.createDate,po.createBy,po.description ,su.`name` as supplyName ,us.userName as createName,po.amount FROM `purchase_order` po left JOIN supply su on po.supplyId=su.id LEFT JOIN `user` us on po.createBy=us.id where po.poNo=?1")
    List<Object[]> findByPoNoWithSupplyName(String poNo);

    @Query(nativeQuery = true, value = "SELECT po.id,po.poNo,po.state,po.supplyId,po.deliverDay,po.payterm,po.createDate,po.createBy,po.description ,su.`name` as supplyName ,us.userName as createName,po.amount FROM `purchase_order` po left JOIN supply su on po.supplyId=su.id LEFT JOIN `user` us on po.createBy=us.id")
    List<Object[]> findAllWithCreate();

    @Modifying
    @Query(value = "update `purchase_order` set formNo=?1,`state`=?2,deliverDate=?3 where poNo=?4",nativeQuery = true)
    void updateInboundstate(String formNo, String state, Date createDate, String sourceNo);

    @Query(nativeQuery = true,value = "SELECT su.`name` as `name` ,sum(po.amount) as `value` FROM `purchase_order` po LEFT JOIN supply su on su.id=po.supplyId where po.createDate>=?1 and po.createDate<?2 group by supplyId ORDER BY `value` desc LIMIT 10;")
    List<Object[]> GetTopSupplyList(String startDate, String endDate);
}
