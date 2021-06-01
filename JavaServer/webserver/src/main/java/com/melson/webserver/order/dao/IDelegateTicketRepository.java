package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.DelegateTicket;
import com.melson.webserver.order.entity.ProducePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IDelegateTicketRepository extends JpaRepository<DelegateTicket,Integer>, JpaSpecificationExecutor<DelegateTicket> {
    DelegateTicket findBySourceIdAndType(Integer sourceId,String type);
    List<DelegateTicket> findByState(String state);
    List<DelegateTicket> findByStateOrState(String state1,String state2);
}
