package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.InventoryOutbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 出库单Repository
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@Repository
public interface IInventoryOutboundRepository extends JpaRepository<InventoryOutbound, Integer> {

    /**
     * 查询指定日期后的出库单
     *
     * @param endDate
     * @return
     */
    @Query
    List<InventoryOutbound> findByCreateDateAfter(Date endDate);

    /**
     * 指定出库单号查询
     *
     * @param formNo
     * @return
     */
    @Query
    InventoryOutbound findByFormNo(String formNo);
}
