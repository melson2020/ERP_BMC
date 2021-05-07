package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.CustomerContact;
import com.melson.webserver.dict.vo.CustomerContactVo;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface ICustomerContact extends IService<CustomerContact> {
    List<CustomerContactVo> getAllContact();

    CustomerContact findById(Integer id);

    CustomerContact findContact(Integer id);

    Result SaveAndUpdate(CustomerContact contact);

    Integer DeleteContact(Integer id);
}
