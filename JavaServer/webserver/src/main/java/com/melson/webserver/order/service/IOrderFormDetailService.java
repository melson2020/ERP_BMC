package com.melson.webserver.order.service;

import com.melson.webserver.contract.entity.ContractStock;
import com.melson.webserver.order.entity.OrderFormDetail;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/30
 */
public interface IOrderFormDetailService {

    List<OrderFormDetail> createDetailList(List<ContractStock> stockList,Integer orderFormId);

    List<OrderFormDetail> findDetailListByFormId(Integer formId);

    List<OrderFormDetail> saveAll(List<OrderFormDetail> details);

}
