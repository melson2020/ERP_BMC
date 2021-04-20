package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.ContractStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同货物Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractStockRepository extends JpaRepository<ContractStock, Integer> {

    /**
     * 根据合同id查询货物集合
     *
     * @param contractId
     * @return
     */
    @Query
    List<ContractStock> findByContractId(Integer contractId);
}
