package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.ContractExtend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同额外属性Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractExtendRepository extends JpaRepository<ContractExtend, Integer> {

    /**
     * 根据合同id查询额外属性集合
     *
     * @param contractId
     * @return
     */
    @Query
    List<ContractExtend> findByContractId(Integer contractId);

}
