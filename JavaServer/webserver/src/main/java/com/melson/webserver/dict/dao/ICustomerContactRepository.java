package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface ICustomerContactRepository extends JpaRepository<CustomerContact,Integer> {
    List<CustomerContact> findByCustomerNo(String customerNo);

    @Query(value = "SELECT * from customer_contact where status ='Y' and customerNo=?1",nativeQuery = true)
    List<CustomerContact> findByCustomerNoByStatus(String customerNo);

}
