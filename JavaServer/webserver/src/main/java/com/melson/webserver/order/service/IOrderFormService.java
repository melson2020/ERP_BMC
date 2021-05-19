package com.melson.webserver.order.service;

import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.entity.ContractOrg;
import com.melson.webserver.contract.entity.ContractStock;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.vo.OrderFormConfirmVo;
import com.melson.webserver.order.vo.OrderFormInfoVo;
import com.melson.webserver.order.vo.OrderFormVo;
import com.melson.webserver.order.vo.OrderStateSummaryVo;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.List;

/**
 * 订单接口
 *
 * @author wuhuan
 * @date 2021年04月27日
 */
public interface IOrderFormService {

    /**
     * 根据合同id查询订单
     *
     * @param contractId
     * @return
     */
    OrderForm list(Integer contractId);

    
    List<OrderFormDetail> detailList(Integer orderFormId);

    /**
     * 订单下达
     * @param vo
     * @return
     */
    OrderForm confirm(OrderFormConfirmVo vo);

    /**
     * 根据订单id获取vo对象
     *
     * @param id
     * @return
     */
    OrderFormVo get(Integer id);

    /**
     * 保存订单
     *
     * @param vo
     * @param userId
     * @return
     */
    OrderForm save(OrderFormVo vo, int userId);

    /**
     * 根据合同创建订单
     *
     * @param contract
     * @return
     */
    OrderForm create(Contract contract, ContractOrg vendeeInfo, List<ContractStock> stockList);

    /**
     * 作废订单
     *
     * @param id
     * @return
     */
    OrderForm invalid(Integer id);

    /**
     * 获取orderForm 各state 数量 用于订单中心 监控
     * @return
     */
   List<OrderStateSummaryVo>  GetStateSummary();

    /**
     *获取OrderForm 各produceType 数量
     */
    List<OrderStateSummaryVo>  GetProduceTypeSummary(String date);

    List<OrderForm> GetOrderFormListByState(String state);

    /**
     *
     * @param id
     * @return
     */
    OrderFormInfoVo GetOrderFormInfo(Integer id);
}
