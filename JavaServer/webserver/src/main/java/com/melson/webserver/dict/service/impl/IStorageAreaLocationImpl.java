package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.webserver.dict.dao.IStorageAreaLocationRepository;
import com.melson.webserver.dict.entity.StorageAreaLocation;
import com.melson.webserver.dict.service.IStorageAreaLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Messi on 2021/5/10
 */
@Service
public class IStorageAreaLocationImpl extends AbstractService<StorageAreaLocation> implements IStorageAreaLocation {
    private final IStorageAreaLocationRepository storageAreaLocationRepository;

    public IStorageAreaLocationImpl(IStorageAreaLocationRepository storageAreaLocationRepository) {
        this.storageAreaLocationRepository = storageAreaLocationRepository;
    }

    @Override
    public JpaRepository<StorageAreaLocation, String> getRepository() {
        return storageAreaLocationRepository;
    }


    @Override
    public List<StorageAreaLocation> findAll() {
        return storageAreaLocationRepository.findAll();
    }

    @Override
    public Result SaveAndUpdate(StorageAreaLocation storageAreaLocation) {
        Result result = new Result();
        StorageAreaLocation checkExist=storageAreaLocationRepository.findByName(storageAreaLocation.getName());
        if(checkExist!=null){
            if(storageAreaLocation.getId()==checkExist.getId()){
                StorageAreaLocation saved=storageAreaLocationRepository.save(storageAreaLocation);
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
                result.setMessage("已经存在此类别名称或联系管理员！");
            }
        }
        else
        {
            StorageAreaLocation saved=storageAreaLocationRepository.save(storageAreaLocation);
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
    @Transactional
    public Integer DeleteStorageAreaLocationService(Integer id) {
        return storageAreaLocationRepository.deleteStorageAreaLocationById(id);
    }

    @Override
    public StorageAreaLocation Query(String storageCode) {
        return storageAreaLocationRepository.findByStorageCode(storageCode);
    }
}
