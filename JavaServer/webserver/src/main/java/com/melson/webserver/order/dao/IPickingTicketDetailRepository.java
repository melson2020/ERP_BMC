package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PickingTicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IPickingTicketDetailRepository extends JpaRepository<PickingTicketDetail, Integer> {
    List<PickingTicketDetail> findByTicketId(Integer ticketId);

    List<PickingTicketDetail> findByTicketIdAndType(Integer ticketId, String type);

    @Query(nativeQuery = true, value = "SELECT ptd.materialNo,ptd.materialName,p.specification,ptd.count,p.salesPrice as latestPrice,ptd.countUnit as unit,p.storageCode,p.id as materialId FROM `picking_ticket_detail` ptd RIGHT JOIN product p on ptd.materialNo=p.productNo  where ticketId=?1 and ptd.type='D'")
    List<Object[]> findInBoundListByTicketId(Integer ticketId);


    @Query(nativeQuery = true,value = "")
    List<Object[]> findOutBoundListByTicketId(Integer ticketId);
}
