package com.melson.webserver.inventory.service;

import com.melson.webserver.inventory.entity.InventoryOutbound;
import com.melson.webserver.inventory.vo.InventoryOutboundDetailVo;
import com.melson.webserver.inventory.vo.InventoryOutboundVo;

import java.util.Date;
import java.util.List;

/**
 * 出库接口
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
public interface IInventoryOutboundService {

    /**
     * 指定结束日期查询出库单
     *
     * @param date
     * @return
     */
    List<InventoryOutboundVo> list(Date date);

    /**
     * 根据入库单号查询出库单
     *
     * @param formNo
     * @return
     */
    InventoryOutboundVo get(String formNo);

    /**
     * 入库
     *
     * @param vo
     * @param userId
     * @return
     */
    InventoryOutbound save(InventoryOutboundVo vo, Integer userId);

    InventoryOutboundVo GenerateOutBoundWithExistTicket(Integer ticketId,String ticketType,Integer userId) throws RuntimeException;

    List<InventoryOutboundDetailVo> GenerateDetailVoBatchInfo(InventoryOutboundDetailVo vo);
}
