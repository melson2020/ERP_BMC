package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.InventoryOutboundDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出库明细Repository
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@Repository
public interface IInventoryOutboundDetailRepository extends JpaRepository<InventoryOutboundDetail, Integer> {

    /**
     * 指定出库单号查询出库明细
     *
     * @param formNo
     * @return
     */
    @Query
    List<InventoryOutboundDetail> findByFormNo(String formNo);
}
