package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IStorageBatchRepository;
import com.melson.webserver.dict.entity.StorageBatch;
import com.melson.webserver.dict.service.IStorageBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/5/10
 */
@Service
public class IStorageBatchImpl extends AbstractService<StorageBatch> implements IStorageBatch {
    private final IStorageBatchRepository storageBatchRepository;

    public IStorageBatchImpl(IStorageBatchRepository storageBatchRepository) {
        this.storageBatchRepository = storageBatchRepository;
    }

    @Override
    public JpaRepository<StorageBatch, String> getRepository() {
        return storageBatchRepository;
    }
}
