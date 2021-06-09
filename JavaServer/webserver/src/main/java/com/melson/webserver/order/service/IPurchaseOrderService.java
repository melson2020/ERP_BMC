package com.melson.webserver.order.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.order.entity.PurchaseOrder;

import java.util.List;

/**
 * Created by Messi on 2021/6/5
 */
public interface IPurchaseOrderService extends IService<PurchaseOrder> {
    List<PurchaseOrder> GetAllCreatedPoList(String state);

    Result Save(PurchaseOrder po);

    PurchaseOrder QueryPo(PurchaseOrder po);

    List<PurchaseOrder> GetAllWithCreate();

}
