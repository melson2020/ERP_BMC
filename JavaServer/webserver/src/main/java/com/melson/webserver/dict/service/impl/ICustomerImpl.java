package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.service.ICustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ICustomerImpl extends AbstractService<Customer> implements ICustomer {
    @Override
    public JpaRepository<Customer, String> getRepository() {
        return null;
    }
}
