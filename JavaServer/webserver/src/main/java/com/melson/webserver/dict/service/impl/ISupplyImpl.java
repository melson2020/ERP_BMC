package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.webserver.dict.dao.ISupplyRepository;
import com.melson.webserver.dict.entity.Supply;
import com.melson.webserver.dict.service.ISupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Messi on 2021/5/13
 */
@Service
public class ISupplyImpl extends AbstractService<Supply> implements ISupply {
    private final ISupplyRepository supplyRepository;

    public ISupplyImpl(ISupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @Override
    public JpaRepository<Supply, String> getRepository() {
        return supplyRepository;
    }

    @Override
    public List<Supply> findAll() {
        return supplyRepository.findAll();
    }

    @Override
    public Result SaveAndUpdate(Supply supply) {
        Result result = new Result();
        Supply checkExist=supplyRepository.findByName(supply.getName());
        if(checkExist!=null){
            if(supply.getId()==checkExist.getId()){
                Supply saved=supplyRepository.save(supply);
                if(saved==null){
                    result.setResultStatus(-1);
                    result.setMessage("保存失败！");
                }else {
                    result.setData(saved);
                }
            }
            else
            {
                result.setResultStatus(-1);
                result.setMessage("已经存在此物料名称或联系管理员！");
            }
        }
        else
        {
            Supply saved=supplyRepository.save(supply);
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            }else {
                result.setData(saved);
            }
        }
        return result;
    }

    @Override
    public Supply Query(Integer id) {
        return supplyRepository.findById(id);
    }

    @Override
    @Transactional
    public Integer DeleteSupply(Integer id) {
        return supplyRepository.deleteSupplyById(id);
    }
}
