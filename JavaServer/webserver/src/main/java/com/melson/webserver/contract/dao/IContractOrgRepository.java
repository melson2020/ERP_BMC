package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.ContractOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 合同公司Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractOrgRepository extends JpaRepository<ContractOrg, Integer> {
}
