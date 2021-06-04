package com.melson.webserver.order.service.impl;

import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.order.dao.IPurchaseDetailRepository;
import com.melson.webserver.order.dao.IPurchasePlanRepository;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.PurchaseDetail;
import com.melson.webserver.order.entity.PurchasePlan;
import com.melson.webserver.order.service.IPurchaseDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final IBomsRepository bomsRepository;
    private final IPurchaseDetailRepository purchaseDetailRepository;
    private final IPurchasePlanRepository purchasePlanRepository;

    public PurchaseDetailServiceImpl(IBomsRepository bomsRepository, IPurchaseDetailRepository purchaseDetailRepository, IPurchasePlanRepository purchasePlanRepository) {
        this.bomsRepository = bomsRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
        this.purchasePlanRepository = purchasePlanRepository;
    }

    /**
     * 生产订单采购明细
     * @param detailList
     * @param formVo
     * @return
     */
    public List<PurchaseDetail> GenerateOrderPurchase(List<OrderFormDetail> detailList, OrderForm formVo){
        if(detailList==null||detailList.size()<=0)return null;
        List<PurchaseDetail> orderPurchaseList=new ArrayList<>();
        PurchasePlan pr=new PurchasePlan();
        pr.setOrderFormId(formVo.getId());
        pr.setOrderFormNo(formVo.getFormNo());
        pr.setCustomerNo(formVo.getCustomerNo());
        pr.setCustomerName(formVo.getCustomerName());
        pr.setContractNo(formVo.getContractNo());
        pr.setType(PurchasePlan.PURCHASE_TYPE_ORDER);
        pr.setState(PurchasePlan.PURCHASE_STATE_CREATE);
        pr.setCreateDate(formVo.getCreateDate());
//        pr.setCreateBy(formVo.getCreateUser());
        pr.setRequester(PurchasePlan.PURCHASE_ACCOUNT_MRP);
        pr.setDescription(formVo.getFormNo()+" "+formVo.getContractNo());
        PurchasePlan saved=purchasePlanRepository.save(pr);
        Integer length=8;
        pr.setPlanNo(NumUtil.incrementCode(pr.getId(), PurchasePlan.PURCHASE_NO_CHAR,length));
        purchasePlanRepository.save(pr);
        for(OrderFormDetail detail:detailList){
            orderPurchaseList.add(CreatePurchase(detail,pr));
        }
        return   purchaseDetailRepository.saveAll(orderPurchaseList);
    }

    @Override
    public List<PurchaseDetail> FindByOrderFormId(Integer orderFormId) {
        return purchaseDetailRepository.findBySourceIdAndType(orderFormId,PurchasePlan.PURCHASE_TYPE_ORDER);
    }

    private PurchaseDetail CreatePurchase(OrderFormDetail formDetail, PurchasePlan pr){
        PurchaseDetail purchaseDetail=new PurchaseDetail();
        purchaseDetail.setType(pr.getType());
        purchaseDetail.setSourceId(formDetail.getOrderFormId());
        purchaseDetail.setMaterialNo(formDetail.getProductNo());
        purchaseDetail.setMaterialName(formDetail.getProductName());
        purchaseDetail.setSpecification(formDetail.getSpecification());
        purchaseDetail.setRemark(formDetail.getRemark());
        purchaseDetail.setCount(new BigDecimal(formDetail.getCount()));
        purchaseDetail.setCountUnit(formDetail.getCountUnit());
        purchaseDetail.setCreateDate(new Date());
        purchaseDetail.setState(pr.getState());
        purchaseDetail.setPurchasePlanNo(pr.getPlanNo());
        purchaseDetail.setCreateBy(pr.getCreateBy());
        purchaseDetail.setRequester(pr.getRequester());
        purchaseDetail.setSupplyId(formDetail.getSupplyId());
        return purchaseDetail;
    }
}
