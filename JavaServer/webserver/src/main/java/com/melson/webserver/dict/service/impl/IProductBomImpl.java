package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.webserver.dict.dao.IProductBomRepository;
import com.melson.webserver.dict.entity.ProductBom;
import com.melson.webserver.dict.service.IProductBom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<ProductBom> findProductBoms(String productNo) {
        List<ProductBom> list=productBomRepository.findByProductNo(productNo);
        return list;
    }

    @Override
    public Result SaveAndUpdate(ProductBom pb) {
        Result result=new Result();
        ProductBom productBom=productBomRepository.save(pb);
        result.setData(productBom);
        return result;
    }


    @Override
    public ProductBom UpdateProductBom(ProductBom pb) {
        return productBomRepository.save(pb);
    }

    @Override
    public List<ProductBom> GetProductBomList() {
        return productBomRepository.findAll();
    }

    @Override
    public ProductBom Query(String bomNo) {
        return productBomRepository.findByBomNo(bomNo);
    }
}
