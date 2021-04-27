package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IProductBomRepository;
import com.melson.webserver.dict.entity.ProductBom;
import com.melson.webserver.dict.service.IProductBom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProductBomImpl extends AbstractService<ProductBom> implements IProductBom {
    private final IProductBomRepository productBomRepository;

    public IProductBomImpl(IProductBomRepository productBomRepository) {
        this.productBomRepository = productBomRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return productBomRepository;
    }
}