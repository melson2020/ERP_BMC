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

    @Override
    public List<PurchaseDetail> GeneratePurchaseDetail(List<OrderFormDetail> detailList, OrderForm form) {
        if (detailList == null || detailList.size() < 0) return null;
        Map<String,OrderFormDetail> orderFormDetailMap=new HashMap<>();
        detailList.forEach(formDetail -> {
            orderFormDetailMap.put(formDetail.getBomNo(),formDetail);
        });
        List<Object[]> objects = bomsRepository.findBomInfoByNos(orderFormDetailMap.keySet());
        List<BomVo> voList = EntityUtils.castEntity(objects, BomVo.class, new BomVo());
        List<BomVo> rootList=GetRootList(voList);
        //物料按照物料号归类
        Map<String,List<BomVo>> materialMap=new HashMap<>();
        for (BomVo vo:rootList){
            List<BomVo> existList=materialMap.get(vo.getChPartNo());
            if(existList==null){
                existList=new ArrayList<>();
                existList.add(vo);
                materialMap.put(vo.getChPartNo(),existList);
            }else {
                existList.add(vo);
            }
        }

        List<PurchaseDetail> purchaseDetailList=new ArrayList<>();
        for(List<BomVo> bomVos :materialMap.values()){
            PurchaseDetail purchaseDetail=CreatedPurchaseDetail(bomVos,orderFormDetailMap);
            purchaseDetailList.add(purchaseDetail);
        }
        return purchaseDetailRepository.saveAll(purchaseDetailList);
    }

    private PurchaseDetail CreatedPurchaseDetail(List<BomVo> bomVoList,Map<String,OrderFormDetail> orderFormDetailMap) {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        purchaseDetail.setType(PurchaseDetail.PURCHASE_TYPE_ORDER);
        BigDecimal totalCount=new BigDecimal(0);
        String remark="";
        for (BomVo vo:bomVoList){
            totalCount=vo.getChQty().add(totalCount);
            remark=(orderFormDetailMap.get(vo.getBomNo()).getProductName()+":"+vo.getChQty()+";");
        }
        BomVo voExample=bomVoList.get(0);
        purchaseDetail.setMaterialNo(voExample.getChPartNo());
        purchaseDetail.setMaterialName(voExample.getName());
        purchaseDetail.setSpecification(voExample.getSpecification());
        purchaseDetail.setRemark(remark);
        purchaseDetail.setCount(totalCount);
        purchaseDetail.setCountUnit(voExample.getUnit());
        purchaseDetail.setCreateDate(new Date());
        purchaseDetail.setState(PurchaseDetail.PURCHASE_STATE_CREATE);
        return purchaseDetail;
    }

    /**
     * 获取底层物料清单
     */
    private List<BomVo> GetRootList(List<BomVo> voList) {
        Set<String> partNoSet=new HashSet<>();
        voList.forEach(bomVo -> partNoSet.add(bomVo.getBomNo()+bomVo.getPartNo()));
        List<BomVo> rootList=new ArrayList<>();
        for (BomVo vo:voList){
            String key=vo.getBomNo()+vo.getChPartNo();
            if(partNoSet.contains(key))continue;
            rootList.add(vo);
        }
        return rootList;
    }


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

    private PurchaseDetail CreatePurchase(OrderFormDetail formDetail){
        PurchaseDetail purchaseDetail=new PurchaseDetail();
        purchaseDetail.setType(PurchaseDetail.PURCHASE_TYPE_ORDER);
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
