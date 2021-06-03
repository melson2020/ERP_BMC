package com.melson.webserver.order.service;

import com.melson.webserver.delegate.vo.DelegateReleaseVo;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.order.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IDelegateTicketService {
    /**
     * 生成委外单 同时生成领料单
     * @param detailList
     * @param form
     * @return
     */
    DelegateTicket GenerateTicket(List<OrderFormDetail> detailList, OrderForm form);

    /**
     * 委外工序生成 委外清单
     * @param list 委外工序
     * @return
     */
    DelegateTicket GenerateTicketWithProcess(List<ProducePlanProcess> list,ProducePlan plan,List<ProducePlanDetail> detailList);

    DelegateTicket FindByOrderFormId(Integer orderFormId);

    DelegateTicket FindByPlanId(Integer planId);

    List<DelegateTicket> FindByState(String state);

    /**
     * 获取正在执行中的list 包括 state=2 state=3
     * @return
     */
    List<DelegateTicket> FindProcessingList();

    List<DelegateTicket> FindRecordList(Map<String,String[]> kvMap);

    DelegateReleaseVo FindReleaseInfo(Integer ticketId);

    DelegateTicket DelegateTicketConfirm(DelegateReleaseVo releaseVo);

    InventoryInboundVo GenerateInventoryInBound(Integer ticketId,Integer userId);
}
