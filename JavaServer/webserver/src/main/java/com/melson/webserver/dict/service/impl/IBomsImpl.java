package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.dao.IProductBomRepository;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.entity.UserGroup;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.vo.BomProcessVo;
import com.melson.webserver.dict.vo.BomVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IBomsImpl extends AbstractService<Boms> implements IBoms {
    private final IBomsRepository bomsRepository;

    public IBomsImpl(IBomsRepository bomsRepository) {
        this.bomsRepository = bomsRepository;
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
    public Integer DeleteUserGroup(Integer id) {
        Integer count=bomsRepository.deleteByIntId(id);
        return count;
    }

}
