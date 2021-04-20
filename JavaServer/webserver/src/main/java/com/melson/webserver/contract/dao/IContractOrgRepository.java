package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.ContractOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同公司Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractOrgRepository extends JpaRepository<ContractOrg, Integer> {

    /**
     * 根据合同id查询合同公司
     *
     * @param contractId
     * @return
     */
    @Query
    List<ContractOrg> findByContractId(Integer contractId);
}
