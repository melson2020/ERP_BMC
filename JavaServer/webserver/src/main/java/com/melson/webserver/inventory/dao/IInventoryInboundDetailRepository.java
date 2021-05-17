package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.InventoryInboundDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 入库明细Repository
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
@Repository
public interface IInventoryInboundDetailRepository extends JpaRepository<InventoryInboundDetail, Integer> {

    /**
     * 指定入库单号查询入库明细
     *
     * @param formNo
     * @return
     */
    @Query
    List<InventoryInboundDetail> findByFormNo(String formNo);
}
