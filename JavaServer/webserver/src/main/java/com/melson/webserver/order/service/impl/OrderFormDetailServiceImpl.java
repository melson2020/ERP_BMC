package com.melson.webserver.order.service.impl;

import com.melson.webserver.contract.entity.ContractStock;
import com.melson.webserver.order.dao.IOrderFormDetailRepository;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.service.IOrderFormDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/30
 */
@Service
public class OrderFormDetailServiceImpl implements IOrderFormDetailService {
    private static final Logger logger = LoggerFactory.getLogger(OrderFormDetailServiceImpl.class);
    @Autowired
    private IOrderFormDetailRepository orderFormDetailRepository;

    @Override
    public List<OrderFormDetail> createDetailList(List<ContractStock> stockList,Integer orderFormId) {
         if(null==stockList||stockList.size()<=0)return null;
         List<OrderFormDetail> detailList=new ArrayList<>(stockList.size());
         for (ContractStock stock:stockList){
             OrderFormDetail detail=new OrderFormDetail();
             detail.setOrderFormId(orderFormId);
             detail.setProductName(stock.getProductName());
             detail.setProductId(stock.getProductId());
             detail.setCount(stock.getCount());
             detail.setProductNo(stock.getProductNo());
             detail.setCountUnit(stock.getCountUnit());
             detail.setSpecification(stock.getSpecification());
             detail.setRemark(stock.getRemark());
             detailList.add(detail);
         }
         return orderFormDetailRepository.saveAll(detailList);
    }

    @Override
    public List<OrderFormDetail> findDetailListByFormId(Integer formId) {
        return orderFormDetailRepository.findByOrderFormId(formId);
    }

    @Override
    public List<OrderFormDetail> saveAll(List<OrderFormDetail> details) {
        return orderFormDetailRepository.saveAll(details);
    }

}
