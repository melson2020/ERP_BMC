package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.DelegateTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IDelegateTicketRepository extends JpaRepository<DelegateTicket,Integer> {
    DelegateTicket findBySourceIdAndType(Integer sourceId,String type);
}
