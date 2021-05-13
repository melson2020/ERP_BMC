package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IStorageDetailRepository;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IStorageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Messi on 2021/5/10
 */
@Service
public class IStorageDetailImpl extends AbstractService<StorageDetail> implements IStorageDetail {
    private final IStorageDetailRepository storageDetailRepository;

    public IStorageDetailImpl(IStorageDetailRepository storageDetailRepository) {
        this.storageDetailRepository = storageDetailRepository;
    }

    @Override
    public JpaRepository<StorageDetail, String> getRepository() {
        return storageDetailRepository;
    }

    @Override
    public StorageDetail findByProductMaterialNo(String productNo) {
        return storageDetailRepository.findByMaterialNo(productNo);
    }

    @Override
    @Transactional
    public Integer DeleteStorage(String materialNo) {
        return storageDetailRepository.deleteByMaterialNo(materialNo);
    }
}
