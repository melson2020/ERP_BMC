package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IBomLogsDetailRepository;
import com.melson.webserver.dict.dao.IBomLogsRepository;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.entity.BomLogs;
import com.melson.webserver.dict.entity.BomLogsDetail;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.vo.BomProcessVo;
import com.melson.webserver.dict.vo.BomVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IBomsImpl extends AbstractService<Boms> implements IBoms {
    private final IBomsRepository bomsRepository;
    private final IBomLogsRepository bomLogsRepository;
    private final IBomLogsDetailRepository bomLogsDetailRepository;

    public IBomsImpl(IBomsRepository bomsRepository, IBomLogsRepository bomLogsRepository, IBomLogsDetailRepository bomLogsDetailRepository) {
        this.bomsRepository = bomsRepository;
        this.bomLogsRepository = bomLogsRepository;
        this.bomLogsDetailRepository = bomLogsDetailRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return bomsRepository;
    }

    @Override
    public List<Boms> findByBomNo(String bomNo) {
       List<Boms> boms=bomsRepository.findByBomNo(bomNo);
//       List<BomVo> voList= EntityUtils.castEntity(objects,BomVo.class,new BomVo());
//       List<BomVo> treeList=GenerateBomTree(voList);
       return  boms;
    }

    @Override
    public List<BomVo> findBomVoInBomNos(Set<String> bomNos) {
        List<Object[]> objects=bomsRepository.findBomInfoInBomNos(bomNos);
        List<BomVo> voList= EntityUtils.castEntity(objects,BomVo.class,new BomVo());
        return voList;
    }

    @Override
    public List<BomProcessVo> findBomProcessVoInBomNos(Set<String> bomNos) {
        List<Object[]> list=bomsRepository.findBomProcessInfoInBomNos(bomNos);
        List<BomProcessVo> voList= EntityUtils.castEntity(list,BomProcessVo.class,new BomProcessVo());
        return voList;
    }

    @Override
    public Result SaveAndUpdate(Boms bom) {
        Result result = new Result();
        Boms saved=bomsRepository.save(bom);
        if(saved==null){
            result.setResultStatus(-1);
            result.setMessage("保存失败！");
        }else {
            result.setData(saved);
        }
        return result;
    }

    @Override
    @Transactional
    public Integer DeleteUserGroup(Boms bom) {
        BomLogs bl=new BomLogs();                  //第一步， 写日志信息
        bl.setType("删除物料");
        bl.setBomNo(bom.getBomNo());
        bl.setModify("删除: "+bom.getChPartNo()+" "+bom.getChName());
        bl.setModifyBy(bom.getCurrentUser());
        bl.setModifyDate(new Date());
        BomLogs saved=bomLogsRepository.save(bl);
        BomLogsDetail bld=new BomLogsDetail();     //第一步， 写日志信息
        bld.setBomLogId(saved.getId());
        bld.setBomId(bom.getId());
        bld.setBomNo(bom.getBomNo());
        bld.setBomGenericId(bom.getBomGenericId());
        bld.setProcessNo(bom.getProcessNo());
        bld.setPartNo(bom.getPartNo());
        bld.setChPartNo(bom.getChPartNo());
        bld.setChBomStatus(bom.getChBomStatus());
        bld.setSpecification(bom.getSpecification());
        bld.setChQty(bom.getChQty());
        bld.setLossRate(bom.getLossRate());
        bld.setProcessStation(bom.getProcessStation());
        bld.setSupplyId(bom.getSupplyId());
        bomLogsDetailRepository.save(bld);
        Integer count=bomsRepository.deleteByIntId(bom.getId());
        return count;
    }

}
