package com.melson.webserver.order.service.impl;

import com.melson.webserver.order.dao.IProducePlanDetailRepository;
import com.melson.webserver.order.dao.IProducePlanRepository;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.order.entity.ProducePlanDetail;
import com.melson.webserver.order.service.IProducePlanService;
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
public class ProducePlanServiceImpl implements IProducePlanService {
    private static final Logger logger = LoggerFactory.getLogger(ProducePlanServiceImpl.class);

    @Autowired
    private IProducePlanRepository producePlanRepository;
    @Autowired
    private IProducePlanDetailRepository producePlanDetailRepository;

    @Override
    public ProducePlan GeneratePlan(List<OrderFormDetail> details, OrderForm form) {
        Map<String,Integer> typeMap=new HashMap<>(4);
        typeMap.put("P",1);
        typeMap.put("C",2);
        typeMap.put("D",3);
        typeMap.put("W",4);
        if(details==null||details.size()<=0)return null;
        ProducePlan producePlan=new ProducePlan();
        producePlan.setOrderFormId(form.getId());
        producePlan.setOrderFormNo(form.getFormNo());
        producePlan.setCustomerNo(form.getCustomerNo());
        producePlan.setCustomerName(form.getCustomerName());
        producePlan.setContractNo(form.getContractNo());
        producePlan.setPlanNo("PP"+System.currentTimeMillis());
        producePlan.setCreateDate(new Date());
        producePlan.setState(ProducePlan.CREATED);
        details.sort(new Comparator<OrderFormDetail>() {
            @Override
            public int compare(OrderFormDetail o1, OrderFormDetail o2) {
                boolean res=typeMap.get(o1.getProduceType())>typeMap.get(o2.getProduceType());
                return res?-1:0;
            }
        });
        List<ProducePlanDetail> producePlanDetails=new ArrayList<>();
        String producePlanType="";
        for(OrderFormDetail detail:details){
            producePlanDetails.add(CreateProducePlanDetail(detail));
            if(!producePlanType.contains(detail.getProduceType())){
                producePlanType+=("/"+detail.getProduceType());
            }
        }
        producePlan.setType(producePlanType);
        producePlanRepository.saveAndFlush(producePlan);
        producePlanDetails.forEach(producePlanDetail -> {producePlanDetail.setProducePlanId(producePlan.getId());});
        producePlanDetailRepository.saveAll(producePlanDetails);
        return producePlan;
    }

    private ProducePlanDetail CreateProducePlanDetail(OrderFormDetail detail){
        ProducePlanDetail producePlanDetail=new ProducePlanDetail();
        producePlanDetail.setProductId(detail.getProductId());
        producePlanDetail.setBomNo(detail.getBomNo());
        producePlanDetail.setRemark(detail.getRemark());
        producePlanDetail.setCount(new BigDecimal(detail.getCount()));
        producePlanDetail.setCountUnit(detail.getCountUnit());
        producePlanDetail.setProduceType(detail.getProduceType());
        return producePlanDetail;
    }

}
