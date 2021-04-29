package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.vo.ContractShowVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同定义Repository
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
@Repository
public interface IContractRepository extends JpaRepository<Contract, Integer> {

    @Query(nativeQuery = true, value = "select c.id,c.orderTicketNo,c.contractNo,co.name as customerName,c.createDate,u.userName as createEmployee,c.type,c.state,c.formalDate from contract c right join contract_org co on co.contractId = c.id RIGHT JOIN `user` u on c.createUser=u.id  where c.type = :type and c.state<>'0' and co.type = '1'")
    List<Object[]> findIntentionList(@Param("type") String type);

    Contract findByContractNo(String contractNo);

}
