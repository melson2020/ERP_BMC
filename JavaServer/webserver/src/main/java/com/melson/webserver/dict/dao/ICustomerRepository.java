package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByCustomerNo(String customerNo);

    Customer findByName(String name);

    @Modifying
    @Query(value = "update customer set `status`='N' where id=?1",nativeQuery = true)
    Integer disableCustomer(Integer customerId);

    @Modifying
    @Query(value = "update customer set `status`=?1 where id=?2",nativeQuery = true)
    Integer updateCustomerStatus(String status, Integer id);
}
