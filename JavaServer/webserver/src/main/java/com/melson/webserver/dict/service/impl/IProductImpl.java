package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.service.IProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProductImpl extends AbstractService<Product> implements IProduct {
    @Override
    public JpaRepository<Product, String> getRepository() {
        return null;
    }
}
