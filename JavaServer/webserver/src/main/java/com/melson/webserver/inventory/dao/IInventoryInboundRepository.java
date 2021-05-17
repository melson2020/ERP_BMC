package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.InventoryInbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 入库单Repository
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
@Repository
public interface IInventoryInboundRepository extends JpaRepository<InventoryInbound, Integer> {

    /**
     * 查询指定日期后的入库单
     *
     * @param endDate
     * @return
     */
    @Query
    List<InventoryInbound> findByCreateDateAfter(Date endDate);

    /**
     * 指定入库单号查询
     *
     * @param formNo
     * @return
     */
    @Query
    InventoryInbound findByFormNo(String formNo);
}
