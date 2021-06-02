package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.DelegateDetail;
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
public interface IDelegateDetailRepository extends JpaRepository<DelegateDetail, Integer> {
    List<DelegateDetail> findByDelegateTicketId(Integer ticketId);

    @Query(value = "SELECT dd.objectNo as materialNo,dd.objectName as materialName,dd.specification, dd.count, dd.delegatePrice as latestPrice, dd.countUnit as unit,p.storageCode FROM `delegate_detail` dd RIGHT JOIN product p on dd.objectNo=p.productNo where dd.delegateTicketId= ?1", nativeQuery = true)
    List<Object[]> findInBoundDetailList(Integer ticketId);
}
