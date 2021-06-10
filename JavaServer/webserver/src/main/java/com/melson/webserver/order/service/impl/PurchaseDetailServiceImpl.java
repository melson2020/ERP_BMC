package com.melson.webserver.order.service.impl;

import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.order.dao.IPurchaseDetailRepository;
import com.melson.webserver.order.dao.IPurchasePlanRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IPurchaseDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private final EntityManagerUtil entityManagerUtil;

    public PurchaseDetailServiceImpl(IBomsRepository bomsRepository, IPurchaseDetailRepository purchaseDetailRepository, IPurchasePlanRepository purchasePlanRepository, EntityManagerUtil entityManagerUtil) {
        this.bomsRepository = bomsRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
        this.purchasePlanRepository = purchasePlanRepository;
        this.entityManagerUtil = entityManagerUtil;
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

    @Override
    public List<PurchaseDetail> GetAllApprovedPurchaseDetailList(String state) {
        return purchaseDetailRepository.findByState(state);
    }

    @Override
    @Transactional
    public Integer DeletePurchase(PurchaseDetail pd) {
        return purchaseDetailRepository.deletePdById(pd.getId());
    }

    @Override
    public List<PurchaseDetail> GetAllPdWithSupplyList(String state) {
        String sql ="SELECT pd.id,pd.type,pd.materialNo,pd.materialName,pd.specification,pd.count,pd.countUnit,pd.purchasePlanNo,pd.state,pd.requester,pd.poNo,su.`name`,pd.createDate FROM `purchase_detail` pd LEFT JOIN supply su on su.id=pd.supplyId";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where pd.state='" + state + "' ORDER BY pd.id DESC");
        List<Object[]> objects = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<PurchaseDetail> details=generate(objects);
        return details;
    }

    private List<PurchaseDetail> generate(List<Object[]> objects) {
        List<PurchaseDetail> details=new ArrayList<>();
        for (Object[] obj : objects) {
            PurchaseDetail po=new PurchaseDetail();
            po.setId(obj[0] == null ? null : new Integer((Integer) obj[0]));
            po.setType(obj[1] == null ? null : obj[1].toString());
            po.setMaterialNo(obj[2] == null ? null : obj[2].toString());
            po.setMaterialName(obj[3] == null ? null : obj[3].toString());
            po.setSpecification(obj[4] == null ? null : obj[4].toString());
            po.setCount(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            po.setCountUnit(obj[6] == null ? null : obj[6].toString());
            po.setPurchasePlanNo(obj[7] == null ? null : obj[7].toString());
            po.setState(obj[8] == null ? null : obj[8].toString());
            po.setRequester(obj[9] == null ? null : obj[9].toString());
            po.setPoNo(obj[10] == null ? null : obj[10].toString());
            po.setSupply(obj[11] == null ? null : obj[11].toString());
            po.setCreateDate(obj[12]==null?null:(Timestamp) obj[12]);
            details.add(po);
        }
        return details;
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
