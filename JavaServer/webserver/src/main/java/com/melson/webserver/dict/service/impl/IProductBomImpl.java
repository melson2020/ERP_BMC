package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.service.ISysSequence;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.dao.IProductBomRepository;
import com.melson.webserver.dict.entity.Boms;
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
    private final ISysSequence sysSequenceService;
    private final IBomsRepository bomsRepository;

    public IProductBomImpl(IProductBomRepository productBomRepository, ISysSequence sysSequenceService, IBomsRepository bomsRepository) {
        this.productBomRepository = productBomRepository;
        this.sysSequenceService = sysSequenceService;
        this.bomsRepository = bomsRepository;
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
    @Transactional
    public Result SaveAndUpdate(ProductBom pb) {
        Result result=new Result();
        pb.setBomNo(sysSequenceService.GenerateCode(Boms.BOM_NO_CHAR));
        ProductBom productBom=productBomRepository.save(pb);
        pb.getMaterialVos().forEach(boms->{boms.setBomNo(pb.getBomNo());});  //设置列表中的BomNo;
        List<Boms> bomsDetial=pb.getMaterialVos();
        bomsRepository.saveAll(bomsDetial);
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
