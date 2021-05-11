package com.melson.webserver.order.service;

import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.ProducePlan;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IProducePlanService {
    /**
     *  根据订单 生成对应的生产计划 包括生产类型 代工和生产
     *  同时生成 取料单  to do
     */
    ProducePlan GeneratePlan(List<OrderFormDetail> details, OrderForm form);
    /**
     * find list with state
     */
    List<ProducePlan> FindPlanList(String state);
}
