package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IProductCategoryRepository;
import com.melson.webserver.dict.entity.ProductCategory;
import com.melson.webserver.dict.service.IProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProductCategoryImpl extends AbstractService<ProductCategory> implements IProductCategory {
    private final IProductCategoryRepository productCategoryRepository;

    public IProductCategoryImpl(IProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return productCategoryRepository;
    }
}
