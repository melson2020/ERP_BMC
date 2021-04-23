package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface ICustomerContactRepository extends JpaRepository<CustomerContact,Integer> {
}
