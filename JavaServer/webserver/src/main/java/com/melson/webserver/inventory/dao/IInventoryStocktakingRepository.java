package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.InventoryStocktaking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 盘点单Repository
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
@Repository
public interface IInventoryStocktakingRepository extends JpaRepository<InventoryStocktaking, Integer> {

    /**
     * 查询指定日期后的盘点单
     *
     * @param endDate
     * @return
     */
    @Query
    List<InventoryStocktaking> findByCreateDateAfter(Date endDate);

    /**
     * 指定盘点单号查询
     *
     * @param formNo
     * @return
     */
    @Query
    InventoryStocktaking findByFormNo(String formNo);
}
