package com.melson.webserver.order.service.impl;

import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.vo.BomVo;
import com.melson.webserver.order.dao.IPurchaseDetailRepository;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.PurchaseDetail;
import com.melson.webserver.order.service.IPurchaseDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Service
public class PurchaseDetailServiceImpl implements IPurchaseDetailService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseDetailServiceImpl.class);

    @Autowired
    private IBomsRepository bomsRepository;
    @Autowired
    private IPurchaseDetailRepository purchaseDetailRepository;

    /**
     * 生产订单采购明细
     * @param detailList
     * @return
     */
    public List<PurchaseDetail> GenerateOrderPurchase(List<OrderFormDetail> detailList){
         if(detailList==null||detailList.size()<=0)return null;
         List<PurchaseDetail> orderPurchaseList=new ArrayList<>();
         for(OrderFormDetail detail:detailList){
             orderPurchaseList.add(CreatePurchase(detail));
         }
         return   purchaseDetailRepository.saveAll(orderPurchaseList);
    }

    @Override
    public List<PurchaseDetail> FindByOrderFormId(Integer orderFormId) {
        return purchaseDetailRepository.findBySourceIdAndType(orderFormId,PurchaseDetail.PURCHASE_TYPE_ORDER);
    }

    private PurchaseDetail CreatePurchase(OrderFormDetail formDetail){
        PurchaseDetail purchaseDetail=new PurchaseDetail();
        purchaseDetail.setType(PurchaseDetail.PURCHASE_TYPE_ORDER);
        purchaseDetail.setSourceId(formDetail.getOrderFormId());
        purchaseDetail.setMaterialNo(formDetail.getProductNo());
        purchaseDetail.setMaterialName(formDetail.getProductName());
        purchaseDetail.setSpecification(formDetail.getSpecification());
        purchaseDetail.setRemark(formDetail.getRemark());
        purchaseDetail.setCount(new BigDecimal(formDetail.getCount()));
        purchaseDetail.setCountUnit(formDetail.getCountUnit());
        purchaseDetail.setCreateDate(new Date());
        purchaseDetail.setState(PurchaseDetail.PURCHASE_STATE_CREATE);
        return purchaseDetail;
    }
}
