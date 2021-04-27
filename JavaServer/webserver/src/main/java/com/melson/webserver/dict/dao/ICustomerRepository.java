package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByCustomerNo(String customerNo);
}
