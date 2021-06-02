package com.melson.webserver.inventory.service;

import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.vo.InventoryInboundVo;

import java.util.Date;
import java.util.List;

/**
 * 入库接口
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
public interface IInventoryInboundService {

    /**
     * 指定结束日期查询入库单
     *
     * @param date
     * @return
     */
    List<InventoryInboundVo> list(Date date);

    /**
     * 根据入库单号查询入库单
     *
     * @param formNo
     * @return
     */
    InventoryInboundVo get(String formNo);

    /**
     * 入库
     *
     * @param vo
     * @param userId
     * @return
     */
    InventoryInbound save(InventoryInboundVo vo, Integer userId);


    InventoryInboundVo createInBoundWithTicket(Integer ticketId,String ticketType,Integer userId);
}
