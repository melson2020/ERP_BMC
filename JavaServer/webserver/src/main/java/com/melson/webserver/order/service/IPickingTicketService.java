package com.melson.webserver.order.service;

import com.melson.webserver.inventory.dto.OutboundRequireDto;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.inventory.vo.InventoryOutboundVo;
import com.melson.webserver.order.entity.*;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IPickingTicketService {
    /**
     * 生产计划 工序生成领料单 包括自产和代工（产品代工领料需要标明，代工一般委托方供料，委外工序也要标明）
     * @return
     */
    PickingTicket GeneratePickTicketWithPlanProcess(List<ProducePlanProcess> processList, ProducePlan plan, List<ProducePlanDetail> detailList);

    /**
     * 订单（order）委外生产取料单
     * @return
     */
    PickingTicket GeneratePickTicketWithOrderFormDetail(OrderForm form,List<OrderFormDetail> details);

    PickingTicket FindByPlanId(Integer planId);

    PickingTicket FindBySourceIdAndType(Integer sourceId,String type);

    List<PickingTicket> FindByCustomerMaterialFlagAndState(String flag,String state);

    List<PickingTicket> FindOutBoundPickingList();

    InventoryInboundVo GenerateInventoryInBound(Integer ticketId, Integer userId);

    int UpdateAfterInBound(Integer id,String inboundNo,String state);

    InventoryOutboundVo GenerateInventoryOutBound (Integer ticketId, Integer userId) throws RuntimeException;

    List<OutboundRequireDto> GenerateOutRequireList(Integer ticketId);

    PickingTicket FindById(Integer id);
}
