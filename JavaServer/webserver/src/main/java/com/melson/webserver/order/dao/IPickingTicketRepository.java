package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PickingTicket;
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
public interface IPickingTicketRepository extends JpaRepository<PickingTicket, Integer> {
    PickingTicket findBySourceIdAndType(Integer id, String type);

    List<PickingTicket> findByCustomerMaterialFlagAndState(String flag, String state);

    List<PickingTicket> findByStateOrState(String state1,String state2);

    @Query(nativeQuery = true, value = "UPDATE picking_ticket pt set pt.state= ?3,pt.inboundTicketNo= ?2 WHERE pt.id= ?1")
    @Modifying
    int updateAfterInBound(Integer id, String inboundNo, String state);

    @Modifying
    @Query(value = "delete from picking_ticket where sourceNo =?1",nativeQuery = true)
    void deleteByByPrNo(String planNo);
}
