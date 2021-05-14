package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IBomsRepository;
import com.melson.webserver.dict.dao.IProductBomRepository;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.vo.BomProcessVo;
import com.melson.webserver.dict.vo.BomVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
    public List<BomVo> findByBomNo(String bomNo) {
       List<Object[]> objects=bomsRepository.findBomInfoByNo(bomNo);
       List<BomVo> voList= EntityUtils.castEntity(objects,BomVo.class,new BomVo());
       List<BomVo> treeList=GenerateBomTree(voList);
       return  treeList;
    }

    @Override
    public List<BomVo> findBomVoInBomNos(Set<String> bomNos) {
        List<Object[]> objects=bomsRepository.findBomInfoByNos(bomNos);
        List<BomVo> voList= EntityUtils.castEntity(objects,BomVo.class,new BomVo());
        return voList;
    }

    @Override
    public List<BomProcessVo> findBomProcessVoByNo(Set<String> bomNos) {
        List<Object[]> list=bomsRepository.findBomProcessInfoByNo(bomNos);
        List<BomProcessVo> voList= EntityUtils.castEntity(list,BomProcessVo.class,new BomProcessVo());
        return voList;
    }

    private List<BomVo> GenerateBomTree(List<BomVo> bomVos){
        List<BomVo> res=new ArrayList<>();
        List<BomVo> remindList=new ArrayList<>();
        for (BomVo bomVo:bomVos){
            if(bomVo.getPartNo().equals("-1")){
                res.add(bomVo);
            }else {
                remindList.add(bomVo);
            }
        }
        for(BomVo vo:res){
            vo.setChildList(FindChildList(vo.getChPartNo(),remindList));
        }
        return res;
    }


    private List<BomVo> FindChildList(String childNo,List<BomVo> remindList){
          List<BomVo> childList=new ArrayList<>();
          List<BomVo> reList=new ArrayList<>();
          for (BomVo vo:remindList){
              if(vo.getPartNo().equals(childNo)){
                  childList.add(vo);
              }else {
                  reList.add(vo);
              }
          }
          if(reList.size()<0)return null;
          for(BomVo vo:childList){
              vo.setChildList(FindChildList(vo.getChPartNo(),reList));
          }
          return childList;
    }
}
