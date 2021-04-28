package com.melson.webserver.order.service;

import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.vo.OrderFormVo;

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
    List<OrderForm> list(Integer contractId);

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
    OrderForm create(Contract contract);

    /**
     * 作废订单
     *
     * @param id
     * @return
     */
    OrderForm invalid(Integer id);
}
