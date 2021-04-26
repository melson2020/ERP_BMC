package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.Contact;
import com.melson.webserver.dict.service.IContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IContactImpl extends AbstractService<Contact> implements IContact {
    @Override
    public JpaRepository<Contact, String> getRepository() {
        return null;
    }
}
