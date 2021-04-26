package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IProductRepository;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.service.IProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProductImpl extends AbstractService<Product> implements IProduct {
    private final IProductRepository productRepository;

    public IProductImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return productRepository;
    }
}
