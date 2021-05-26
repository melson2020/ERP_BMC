package com.melson.webserver.order.dao;

import com.melson.webserver.order.entity.ProducePlan;
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
public interface IProducePlanRepository extends JpaRepository<ProducePlan,Integer> {
    List<ProducePlan> findByState(String state);
    ProducePlan findByOrderFormId(Integer orderFormId);

    @Query(nativeQuery = true,value = "SELECT state,count(id) as count FROM `produce_plan` p where p.state<>'3' GROUP BY p.state;")
    List<Object[]> findStateSummary();

    @Query(nativeQuery = true,value = "SELECT pp.id,pp.planNo,pp.orderFormNo,pp.createDate,pp.startDate,pp.endDate,pp.pickingTicketNo,pt.state as pickingState,pp.state as planState FROM `produce_plan` pp RIGHT JOIN picking_ticket pt on pp.pickingTicketNo=pt.ticketNo WHERE pp.state='2';")
    List<Object[]> findProcessingPlanAndPickingTicket();

    @Query(nativeQuery = true,value = "select pp.id as planId,pws.workStationId,pp.state,pp.planNo from produce_plan pp RIGHT JOIN produce_plan_work_station pws on pp.id=pws.planId WHERE pp.state='2';")
    List<Object[]> findProcessingWorkStation();
}
