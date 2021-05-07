package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.PickingTicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IPickingTicketDetailRepository extends JpaRepository<PickingTicketDetail,Integer> {
}
