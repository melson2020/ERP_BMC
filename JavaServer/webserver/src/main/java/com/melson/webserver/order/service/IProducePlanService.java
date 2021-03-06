package com.melson.webserver.order.service;

import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.order.entity.ProducePlanDetail;
import com.melson.webserver.produce.vo.ProducePlanInfoVo;
import com.melson.webserver.produce.vo.ProducePlanPickingTicketVo;
import com.melson.webserver.produce.vo.ProducePlanStateSummaryVo;

import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IProducePlanService {
    /**
     * 根据订单 生成对应的生产计划 包括生产类型 代工和生产
     */
    ProducePlan GeneratePlan(List<OrderFormDetail> details, OrderForm form);

    /**
     * find list with state
     */
    List<ProducePlan> FindPlanList(String state);

    List<ProducePlanDetail> FindPlanDetailList(Integer planId);

    /**
     * 生产计划保存或者保存下达
     *
     * @param plan
     * @param confirm
     * @return
     */
    ProducePlan UpdatePlan(ProducePlan plan, Boolean confirm);

    ProducePlan FindByOrderFormId(Integer orderFormId);

    ProducePlanStateSummaryVo GetStateSummary();

    List<ProducePlanPickingTicketVo> GetProcessingPlanAndPickingTicketInfo();

    ProducePlanInfoVo FindPlanInfo(Integer planId);

    List<ProducePlan> FindProducePlanRecord(Map<String,String[]> kvMap);

    InventoryInboundVo GenerateInventoryInBound(Integer ticketId,Integer userId);

    int UpdatePlanAfterInBound(Integer id,String inboundNo,String state);
}
