package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.ICustomerProductRepository;
import com.melson.webserver.dict.entity.CustomerProduct;
import com.melson.webserver.dict.service.ICustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ICustomerProductImpl extends AbstractService<CustomerProduct> implements ICustomerProduct {
    private final ICustomerProductRepository customerBomRepository;

    public ICustomerProductImpl(ICustomerProductRepository customerBomRepository) {
        this.customerBomRepository = customerBomRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return customerBomRepository;
    }
}
