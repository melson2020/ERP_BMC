package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.ProducePlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IProducePlanDetailRepository extends JpaRepository<ProducePlanDetail,Integer> {
    List<ProducePlanDetail> findByProducePlanId(Integer planId);
    @Query(value = "SELECT p.productNo as materialNo,pd.productName as materialName,pd.specification,pd.count,pd.salePrice as latestPrice,pd.countUnit as unit,p.storageCode,pd.productId as materialId from produce_plan_detail pd RIGHT JOIN product p on pd.productId=p.id WHERE pd.producePlanId= ?1",nativeQuery = true)
    List<Object[]> findInventoryInBoundDetail(Integer ticketId);

}
