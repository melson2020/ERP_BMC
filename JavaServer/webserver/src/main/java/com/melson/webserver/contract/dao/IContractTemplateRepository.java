package com.melson.webserver.contract.dao;

import com.melson.webserver.contract.entity.ContractTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/22
 */
@Repository
public interface IContractTemplateRepository extends JpaRepository<ContractTemplate, Integer> {
    List<ContractTemplate> findByContractType(String type);
}
