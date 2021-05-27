package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.DelegateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Repository
public interface IDelegateDetailRepository  extends JpaRepository<DelegateDetail, Integer> {
    List<DelegateDetail> findByDelegateTicketId(Integer ticketId);
}
