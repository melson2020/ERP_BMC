package com.melson.webserver.order.service.impl;

import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.PurchaseDetail;
import com.melson.webserver.order.service.IPurchaseDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Service
public class PurchaseDetailServiceImpl implements IPurchaseDetailService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseDetailServiceImpl.class);

    @Override
    public List<PurchaseDetail> GeneratePurchaseDetail(List<OrderFormDetail> detailList, OrderForm form) {
        return null;
    }
}
