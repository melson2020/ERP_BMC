package com.melson.webserver.order.service;

import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.PurchaseDetail;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
public interface IPurchaseDetailService {
    /**
     * 生成 采购需求 采购计划由采购中心生成
     * @param detailList
     * @return
     */
    List<PurchaseDetail> GenerateOrderPurchase(List<OrderFormDetail> detailList);

    List<PurchaseDetail> FindByOrderFormId(Integer orderFormId);
}
