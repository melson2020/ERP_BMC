package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.StorageAreaLocation;

import java.util.List;

/**
 * Created by Messi on 2021/5/10
 */
public interface IStorageAreaLocation extends IService<StorageAreaLocation> {
    List<StorageAreaLocation> findAll();
}
