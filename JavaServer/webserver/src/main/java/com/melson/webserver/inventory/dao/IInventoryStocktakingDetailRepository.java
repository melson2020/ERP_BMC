package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.InventoryStocktakingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 盘点明细Repository
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
@Repository
public interface IInventoryStocktakingDetailRepository extends JpaRepository<InventoryStocktakingDetail, Integer> {

    /**
     * 指定盘点单号查询盘点明细
     *
     * @param formNo
     * @return
     */
    @Query
    List<InventoryStocktakingDetail> findByFormNoAndFinish(String formNo,Integer finish);
}
