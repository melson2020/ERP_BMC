package com.melson.webserver.order.service;

import com.melson.webserver.delegate.vo.DelegateReleaseVo;
import com.melson.webserver.order.entity.*;

import java.util.List;

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

    List<DelegateTicket> FindReleaseList();

    /**
     * 获取正在执行中的list 包括 state=2 state=3
     * @return
     */
    List<DelegateTicket> FindProcessingList();

    DelegateReleaseVo FindReleaseInfo(Integer ticketId);

    DelegateTicket DelegateTicketConfirm(DelegateReleaseVo releaseVo);
}
