package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 合同定义Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractRepository extends JpaRepository<Contract, Integer> {

}
