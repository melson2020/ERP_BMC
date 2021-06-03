package com.melson.webserver.order.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.order.dao.IPurchaseDetailRepository;
import com.melson.webserver.order.dao.IPurchasePlanRepository;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.PurchaseDetail;
import com.melson.webserver.order.entity.PurchasePlan;
import com.melson.webserver.order.service.IPurchasePlanService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/6/3
 */
@Service
public class IPurchasePlanServiceImpl extends AbstractService<PurchasePlan> implements IPurchasePlanService {
    private final IPurchasePlanRepository purchasePlanRepository;
    private final IPurchaseDetailRepository purchaseDetailRepository;

    public IPurchasePlanServiceImpl(IPurchasePlanRepository purchasePlanRepository, IPurchaseDetailRepository purchaseDetailRepository) {
        this.purchasePlanRepository = purchasePlanRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
    }


    @Override
    public JpaRepository<PurchasePlan, String> getRepository() {
        return null;
    }

    @Override
    public List<PurchasePlan> GetpurchaseList(Integer userId) {
        return purchasePlanRepository.findByCreateBy(userId);
    }

    @Override
    public Result Save(PurchasePlan pr) {
        Result result=new Result();
        PurchasePlan saved=purchasePlanRepository.save(pr);
        Integer length=8;
        pr.setPlanNo(NumUtil.incrementCode(pr.getId(), PurchasePlan.PURCHASE_NO_CHAR,length));
        purchasePlanRepository.save(pr);
        for(PurchaseDetail detail:pr.getPurchaseDetailList()){
            detail.setType(pr.getType());
            detail.setCreateDate(pr.getCreateDate());
            detail.setState(pr.getState());
            detail.setPurchasePlanNo(pr.getPlanNo());
            detail.setCreateBy(pr.getCreateBy());
            detail.setRequester(pr.getRequester());
        }
        List<PurchaseDetail> detailList=pr.getPurchaseDetailList();
        purchaseDetailRepository.saveAll(detailList);
        result.setData(saved);
        return result;
    }

    @Override
    public PurchasePlan QueryPurchasePlan(String planNo) {
        PurchasePlan pr=purchasePlanRepository.findByPlanNo(planNo);
        List<PurchaseDetail> detailList=purchaseDetailRepository.findByPurchasePlanNo(planNo);
        pr.setPurchaseDetailList(detailList);
        return pr;
    }

    @Override
    @Transactional
    public Integer UpdatePurchasePlanStatus(PurchasePlan purchase) {
        Integer count = purchasePlanRepository.UpdatePurchaseStatus(purchase.getState(),purchase.getId());
        purchaseDetailRepository.UpdateStatus(purchase.getState(),purchase.getPlanNo());
        return count;
    }
}
