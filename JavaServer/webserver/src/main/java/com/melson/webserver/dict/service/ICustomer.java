package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.vo.ContractVo;
import com.melson.webserver.dict.vo.CustomerVo;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface ICustomer extends IService<Customer> {
    List<Customer> findAll();

    List<CustomerVo> findVoAll();

    Customer findCustomerByCustomerNo(String customerNo);

    ContractVo findCustomerVoByCustomerNo(String customerNo);

    Result SaveAndUpdate(Customer customer);

    List<Customer> findAllExclude();

//    Customer Query(Customer customer);

    Integer DisableCustomer(Customer customer);

    Customer Query(Integer id, String customerNo);

    Integer UpdateCustomerStatus(Customer customer);
}
