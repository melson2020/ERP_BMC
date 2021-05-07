package com.melson.webserver.order.service.impl;

import com.melson.webserver.order.entity.DelegateTicket;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.service.IDelegateTicketService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Service
public class DelegateTicketServiceImpl implements IDelegateTicketService {
    @Override
    public DelegateTicket GenerateTicket(List<OrderFormDetail> detailList, OrderForm form) {
        return null;
    }
}
