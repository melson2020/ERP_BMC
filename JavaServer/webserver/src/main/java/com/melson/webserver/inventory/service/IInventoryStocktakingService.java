package com.melson.webserver.inventory.service;

import com.melson.webserver.inventory.entity.InventoryStocktaking;
import com.melson.webserver.inventory.vo.InventoryStocktakingVo;

import java.util.Date;
import java.util.List;

/**
 * 盘点接口
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
public interface IInventoryStocktakingService {

    /**
     * 指定结束日期查询盘点单
     *
     * @param date
     * @return
     */
    List<InventoryStocktakingVo> list(Date date);

    /**
     * 根据盘点单号查询盘点单
     *
     * @param formNo
     * @return
     */
    InventoryStocktakingVo get(String formNo);

    /**
     * 盘点
     *
     * @param vo
     * @param userId
     * @return
     */
    InventoryStocktaking save(InventoryStocktakingVo vo, Integer userId);
}
