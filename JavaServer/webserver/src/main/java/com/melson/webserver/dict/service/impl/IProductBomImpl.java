package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.service.ISysSequence;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.dao.IProductBomRepository;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.entity.ProductBom;
import com.melson.webserver.dict.service.IProductBom;
import com.melson.webserver.dict.vo.GroupProductVo;
import com.mysql.cj.util.StringUtils;
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
    private final IProductImpl productService;

    public IProductBomImpl(IProductBomRepository productBomRepository, ISysSequence sysSequenceService, IBomsRepository bomsRepository, IProductImpl productService) {
        this.productBomRepository = productBomRepository;
        this.sysSequenceService = sysSequenceService;
        this.bomsRepository = bomsRepository;
        this.productService = productService;
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
        if(StringUtils.isNullOrEmpty(pb.getBomNo())) {
            pb.setBomNo(sysSequenceService.GenerateCode(Boms.BOM_NO_CHAR));
        }
        ProductBom productBom=productBomRepository.save(pb);
//        pb.getMaterialVos().forEach(boms->{boms.setBomNo(pb.getBomNo());});  //设置列表中的BomNo;
        List<Boms> bomsDetials=pb.getMaterialVos();
        Boolean flag;
        flag = true;
        for(Boms bo:bomsDetials )
        {
            bo.setBomNo(pb.getBomNo());
            bo.setBomGenericId(pb.getBomGenericId());
            if(bo.getChQty()==null || StringUtils.isNullOrEmpty(bo.getChQty().toString()))
            {
                flag=false;
            }
            if(bo.getLossRate()==null ||StringUtils.isNullOrEmpty(bo.getLossRate().toString()))
            {
                flag=false;
            }
        }
        if(flag){
            bomsRepository.saveAll(bomsDetials);
            result.setData(productBom);
            return result;
        }
        else {
            result.setResultStatus(-1);
            result.setMessage("请检查数量和损耗的数据！");
            return result;
        }
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

    @Override
    public ProductBom QueryProductBomsDetailList(String bomNo) {
        ProductBom pb=productBomRepository.findByBomNo(bomNo);
        List<Boms> materialVos=bomsRepository.findByBomNoAndBomGenericId(bomNo,pb.getBomGenericId());
        List<GroupProductVo> groupProductVos=productService.GetProductVoList(pb.getProductNo());
        pb.setGroupProductVoList(groupProductVos);
        pb.setMaterialVos(materialVos);
        return pb;
    }

    @Override
    public ProductBom QueryProductionBomAll(ProductBom pb) {
        ProductBom pbom=productBomRepository.findByBomNo(pb.getBomNo());
        List<GroupProductVo> groupProductVos=productService.GetProductVoList(pb.getProductNo());
        pbom.setGroupProductVoList(groupProductVos);
        return pbom;
    }
}
