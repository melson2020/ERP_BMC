package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.CustomerContact;
import com.melson.webserver.dict.service.ICustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ICustomerContactImpl extends AbstractService<CustomerContact> implements ICustomerContact {
    @Override
    public JpaRepository<CustomerContact, String> getRepository() {
        return null;
    }
}
