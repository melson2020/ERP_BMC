package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.ContractStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 合同货物Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractStockRepository extends JpaRepository<ContractStock, Integer> {
}
