package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.ICustomerBomRepository;
import com.melson.webserver.dict.entity.CustomerBom;
import com.melson.webserver.dict.service.ICustomerBom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ICustomerBomImpl extends AbstractService<CustomerBom> implements ICustomerBom {
    private final ICustomerBomRepository customerBomRepository;

    public ICustomerBomImpl(ICustomerBomRepository customerBomRepository) {
        this.customerBomRepository = customerBomRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return customerBomRepository;
    }
}
