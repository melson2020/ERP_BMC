package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PurchaseDetail;
import com.melson.webserver.order.vo.DashBoardItemVo;
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

    @Modifying
    @Query(value = "delete from `purchase_detail` where id =?1",nativeQuery = true)
    Integer deletePdById(Integer id);

    List<PurchaseDetail> findByPoNo(String poNo);

    @Query(nativeQuery = true,value = "SELECT pd.materialNo,pd.materialName,pd.specification,pd.count, p.salesPrice as latestPrice,pd.countUnit as unit,p.storageCode,p.id as materialId FROM `purchase_detail` pd RIGHT JOIN product p on pd.materialNo=p.productNo WHERE pd.poNo= ?1")
    List<Object[]> findInboundListByPlanNo(String planNo);

    @Query(nativeQuery = true,value = "SELECT state,count(state) as count from `purchase_detail` where state='BUYING'")
    List<Object[]> getAllStatueCount();

    @Modifying
    @Query(value = "update `purchase_detail` set `state`=?1 where poNo=?2",nativeQuery = true)
    void updateInboundsDetail(String state, String sourceNo);

    @Query(nativeQuery = true,value = "SELECT purchasePlanNo,state FROM `purchase_detail` where poNo=?1")
    List<Object[]> findPrByPoNo(String sourceNo);

    @Query(nativeQuery = true,value = "SELECT state FROM `purchase_detail` where purchasePlanNo=?1")
    List<Object[]> findObjectByPurchasePlanNo(String prNo);

    @Query(nativeQuery = true,value = "SELECT `type`,materialNo, materialName,specification,remark,count,countUnit,purchasePlanNo,createEmployeeNo,createDate,'CREATE' as state,'' as delegateFlag,pickingTicketId as ticketId,productId from purchase_detail WHERE purchasePlanNo=?1 and `type`<>'ORDER'")
    List<Object[]> findPickingDetail(String prNo);

    @Query(nativeQuery = true,value = "SELECT CONCAT(pr.productNo,'/',pr.name) as `name`, sum(pd.count) as `value` from purchase_detail pd LEFT JOIN product pr on pr.id=pd.productId where pd.createDate>=?1 and pd.createDate<?2 GROUP BY pd.productId ORDER BY `value` desc LIMIT 10")
    List<Object[]> GetTopProductList(String startDate, String endDate);
}
