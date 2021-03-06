package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.service.ISysSequence;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IStorageAreaLocationRepository;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.StorageAreaLocation;
import com.melson.webserver.dict.entity.WorkGroup;
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
    private final ISysSequence sysSequenceService;

    public IStorageAreaLocationImpl(IStorageAreaLocationRepository storageAreaLocationRepository, ISysSequence sysSequenceService) {
        this.storageAreaLocationRepository = storageAreaLocationRepository;
        this.sysSequenceService = sysSequenceService;
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
                    result.setMessage("???????????????");
                }else {
                    result.setData(saved);
                }
            }
            else
            {
                result.setResultStatus(-1);
                result.setMessage("????????????????????????????????????????????????");
            }
        }
        else
        {
//            if(storageAreaLocation.getId()==null) {
//                storageAreaLocation.setStorageCode(sysSequenceService.GenerateCode(StorageAreaLocation.STORAGE_NO_CHAR));
//            }
            StorageAreaLocation saved=storageAreaLocationRepository.save(storageAreaLocation);
            Integer length=8;
            storageAreaLocation.setStorageCode(NumUtil.incrementCode(storageAreaLocation.getId(), StorageAreaLocation.STORAGE_NO_CHAR,length));
            storageAreaLocationRepository.save(storageAreaLocation);
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("???????????????");
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
