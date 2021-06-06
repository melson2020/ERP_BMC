package com.melson.base.dao;

import com.melson.base.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/21
 */
@Repository
public interface ICompanyDao extends JpaRepository<Company,String> {
    Company findByPhoneNumber(String phoneNumber);
    Company findByCompanyName(String name);

    Company findByCompanyCode(String companyCode);
}
