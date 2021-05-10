package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IStorageAreaLocationRepository;
import com.melson.webserver.dict.entity.StorageAreaLocation;
import com.melson.webserver.dict.service.IStorageAreaLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
}
