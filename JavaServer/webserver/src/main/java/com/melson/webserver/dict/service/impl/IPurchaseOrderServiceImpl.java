package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.order.entity.PurchaseOrder;
import com.melson.webserver.order.service.IPurchaseOrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/6/5
 */
@Service
public class IPurchaseOrderServiceImpl extends AbstractService<PurchaseOrder> implements IPurchaseOrderService {
    @Override
    public JpaRepository<PurchaseOrder, String> getRepository() {
        return null;
    }
}
