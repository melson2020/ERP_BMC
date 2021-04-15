package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.ContractExtend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 合同额外属性Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractExtendRepository extends JpaRepository<ContractExtend, Integer> {

}
