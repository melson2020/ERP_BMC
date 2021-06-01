package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.service.ISysSequence;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IBomLogsDetailRepository;
import com.melson.webserver.dict.dao.IBomLogsRepository;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.dao.IProductBomRepository;
import com.melson.webserver.dict.entity.BomLogs;
import com.melson.webserver.dict.entity.BomLogsDetail;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.entity.ProductBom;
import com.melson.webserver.dict.service.IProductBom;
import com.melson.webserver.dict.vo.GroupProductVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private final IBomLogsRepository bomLogsRepository;
    private final IBomLogsDetailRepository bomLogsDetailRepository;

    public IProductBomImpl(IProductBomRepository productBomRepository, ISysSequence sysSequenceService, IBomsRepository bomsRepository, IProductImpl productService, IBomLogsRepository bomLogsRepository, IBomLogsDetailRepository bomLogsDetailRepository) {
        this.productBomRepository = productBomRepository;
        this.sysSequenceService = sysSequenceService;
        this.bomsRepository = bomsRepository;
        this.productService = productService;
        this.bomLogsRepository = bomLogsRepository;
        this.bomLogsDetailRepository = bomLogsDetailRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return productBomRepository;
    }

    @Override
    public List<ProductBom> findProductBoms(String productNo) {
        List<ProductBom> list=productBomRepository.findByProductNoAndStatus(productNo,ProductBom.STATUS_USING);
        return list;
    }

    @Override
    @Transactional
    public Result SaveAndUpdate(ProductBom pb) {
        Result result=new Result();
        Boolean newFlag=true;
        BomLogs bl=new BomLogs();                  //第一步， 写日志信息
        ProductBom oldpb=new ProductBom();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String changes="";
        if(!StringUtils.isNullOrEmpty(pb.getBomNo())) {
            newFlag=false;
            oldpb=productBomRepository.findByBomNo(pb.getBomNo());
            if(oldpb.getProcessNo()!=pb.getProcessNo())
            {
                changes=" "+changes+oldpb.getProcessName()+"->"+pb.getProcessName();
            }
            if(oldpb.getExpirationDate()!=pb.getExpirationDate())
            {
                changes=" "+changes+sdf.format(oldpb.getExpirationDate())+"->"+sdf.format(pb.getExpirationDate());
            }
            if(oldpb.getDescription()!=pb.getDescription())
            {
                changes=" "+changes+oldpb.getDescription()+"->"+pb.getDescription();
            }
        }

        ProductBom productBom=productBomRepository.save(pb);
        if(newFlag) {                              //生成 BOM No
            Integer length=8;
            pb.setBomNo(NumUtil.incrementCode(productBom.getId(), Boms.BOM_NO_CHAR,length));
            bl.setType("创建BOM信息");
        }
        else
        {
            bl.setType("修改BOM信息");
        }
        bl.setBomNo(pb.getBomNo());
        bl.setModifyBy(pb.getCreateBy());
        bl.setModifyDate(new Date());
        bl.setModify(changes.trim());
        productBomRepository.save(pb);
        BomLogs savedBomLogs=bomLogsRepository.save(bl);     //第一步， 写日志信息
        List<BomLogsDetail> bldList=new ArrayList<>();       //第二部   保存日志详情
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
            BomLogsDetail bld=new BomLogsDetail();
            bld.setBomLogId(savedBomLogs.getId());
            bld.setBomId(bo.getId());
            bld.setBomNo(bo.getBomNo());
            bld.setBomGenericId(bo.getBomGenericId());
            bld.setProcessNo(bo.getProcessNo());
            bld.setPartNo(bo.getPartNo());
            bld.setChPartNo(bo.getChPartNo());
            bld.setChBomStatus(bo.getChBomStatus());
            bld.setSpecification(bo.getSpecification());
            bld.setChQty(bo.getChQty());
            bld.setLossRate(bo.getLossRate());
            bld.setProcessStation(bo.getProcessStation());
            bld.setSupplyId(bo.getSupplyId());
            bldList.add(bld);
        }
        if(flag){
            bomsRepository.saveAll(bomsDetials);
            bomLogsDetailRepository.saveAll(bldList);
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
