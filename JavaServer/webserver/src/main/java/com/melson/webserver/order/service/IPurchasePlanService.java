package com.melson.webserver.order.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.order.entity.PurchasePlan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/6/3
 */

public interface IPurchasePlanService extends IService<PurchasePlan> {
    List<PurchasePlan> GetpurchaseList(Integer userId);

    Result Save(PurchasePlan purchase);

    PurchasePlan QueryPurchasePlan(String planNo);

    Integer UpdatePurchasePlanStatus(PurchasePlan purchase);

    Integer DeletePurchase(PurchasePlan purchase);

    List<PurchasePlan> GetUnderApprovePurchaseList(String state);

    Result ApprovePurchasePlan(PurchasePlan purchase);

    Integer RejectPurchase(PurchasePlan purchase);
}
