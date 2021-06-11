package com.melson.webserver.order.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.order.dao.IPickingTicketRepository;
import com.melson.webserver.order.dao.IPurchaseDetailRepository;
import com.melson.webserver.order.dao.IPurchasePlanRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IPurchasePlanService;
import com.melson.webserver.order.vo.PurchaseStateSummaryVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Messi on 2021/6/3
 */
@Service
public class IPurchasePlanServiceImpl extends AbstractService<PurchasePlan> implements IPurchasePlanService {
    private final IPurchasePlanRepository purchasePlanRepository;
    private final IPurchaseDetailRepository purchaseDetailRepository;
    private final IPickingTicketRepository pickingTicketRepository;

    public IPurchasePlanServiceImpl(IPurchasePlanRepository purchasePlanRepository, IPurchaseDetailRepository purchaseDetailRepository, IPickingTicketRepository pickingTicketRepository) {
        this.purchasePlanRepository = purchasePlanRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
        this.pickingTicketRepository = pickingTicketRepository;
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
        for(PurchaseDetail detail:pr.getPurchaseDetailList()){
            detail.setType(pr.getType());
            detail.setCreateDate(pr.getCreateDate());
            detail.setState(pr.getState());
            detail.setPurchasePlanNo(pr.getPlanNo());
            detail.setCreateBy(pr.getCreateBy());
            detail.setRequester(pr.getRequester());
            detail.setRequesterId(pr.getRequesterId());
        }
        List<PurchaseDetail> detailList=pr.getPurchaseDetailList();
        purchaseDetailRepository.saveAll(detailList);
        PickingTicket pt=new PickingTicket();                 //创建picking_ticket
        pt.setTicketNo("L"+new Date().getTime());
        pt.setSourceNo(pr.getPlanNo());
        pt.setType(PurchasePlan.PURCHASE_TYPE_INDIRECT);
        pt.setCreateDate(new Date());
        pt.setState(PickingTicket.STATE_CREATE);
        pickingTicketRepository.save(pt);
        pr.setPickingNo(pt.getTicketNo());
        purchasePlanRepository.save(pr);
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

    @Override
    @Transactional
    public Integer DeletePurchase(PurchasePlan purchase) {
        purchaseDetailRepository.deleteByPrNo(purchase.getPlanNo());     //删除PR 详细表
        return purchasePlanRepository.deletePrById(purchase.getId());    //删除PR
    }

    @Override
    public List<PurchasePlan> GetUnderApprovePurchaseList(String state) {
        return purchasePlanRepository.findListByState(state);
    }

    @Override
    public Result ApprovePurchasePlan(PurchasePlan purchase) {
        Result result=new Result();
        purchase.setState(PurchasePlan.PURCHASE_STATE_APPROVE);
        PurchasePlan saved=purchasePlanRepository.save(purchase);
        for(PurchaseDetail detail:purchase.getPurchaseDetailList()){
            detail.setState(purchase.getState());
        }
        List<PurchaseDetail> detailList=purchase.getPurchaseDetailList();
        purchaseDetailRepository.saveAll(detailList);
        result.setData(saved);
        return result;
    }

    @Override
    @Transactional
    public Integer RejectPurchase(PurchasePlan purchase) {
        Integer count = purchasePlanRepository.UpdatePurchaseStatus(purchase.getState(),purchase.getId());
        purchaseDetailRepository.UpdateStatus(purchase.getState(),purchase.getPlanNo());
        return count;
    }

    @Override
    public List<PurchasePlan> findAllPR() {
        return purchasePlanRepository.findAll();
    }

    @Override
    public List<PurchaseStateSummaryVo> GetSummaryCount() {
        List<PurchaseStateSummaryVo> list=new ArrayList<>();
        list.add(new PurchaseStateSummaryVo("采购申请", 0, "/request"));
        List<Object[]> objects = purchasePlanRepository.getAllStatueCount();
        Map<String, Integer> stateMap = new HashMap<>();
        for (Object[] obj : objects) {
            String key = obj[0].toString();
            stateMap.put(key, Integer.parseInt(obj[1].toString()));
        }
        List<Object[]> objects2 = purchaseDetailRepository.getAllStatueCount();
        for (Object[] ob : objects2) {
            if(ob[0]!=null) {
                String key = ob[0].toString();
                stateMap.put(key, Integer.parseInt(ob[1].toString()));
            }
        }
        Integer waitApproveCount = stateMap.get(PurchasePlan.PURCHASE_STATE_CREATE) == null ? 0 : stateMap.get(PurchasePlan.PURCHASE_STATE_CREATE);
        list.add(new PurchaseStateSummaryVo("待批列表", waitApproveCount, "/approve"));
        Integer waitPoCount = stateMap.get(PurchasePlan.PURCHASE_STATE_APPROVE) == null ? 0 : stateMap.get(PurchasePlan.PURCHASE_STATE_APPROVE);
        list.add(new PurchaseStateSummaryVo("等待下单", waitPoCount, "/purchaseOrder"));
        Integer waitDeliver = stateMap.get(PurchasePlan.PURCHASE_STATE_BUYING) == null ? 0 : stateMap.get(PurchasePlan.PURCHASE_STATE_BUYING);
        list.add(new PurchaseStateSummaryVo("待发货", waitDeliver, "/waiting"));
        return list;
    }


}
