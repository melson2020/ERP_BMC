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
     * @param formVo
     * @return
     */
    List<PurchaseDetail> GenerateOrderPurchase(List<OrderFormDetail> detailList, OrderForm formVo);

    List<PurchaseDetail> FindByPlanNo(String planNo);

    List<PurchaseDetail> GetAllApprovedPurchaseDetailList(String state);

    Integer DeletePurchase(PurchaseDetail pd);

    List<PurchaseDetail> GetAllPdWithSupplyList(String state);
}
