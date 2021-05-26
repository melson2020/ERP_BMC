package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PickingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IPickingTicketRepository extends JpaRepository<PickingTicket,Integer> {
    PickingTicket findBySourceIdAndType(Integer id,String type);
}
