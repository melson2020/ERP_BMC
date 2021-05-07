package com.melson.webserver.order.service;

import com.melson.webserver.order.entity.DelegateTicket;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.PurchaseDetail;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IDelegateTicketService {
    /**
     * 生成委托单
     * @param detailList
     * @param form
     * @return
     */
    DelegateTicket GenerateTicket(List<OrderFormDetail> detailList, OrderForm form);
}
