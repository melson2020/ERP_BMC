package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.StorageDetail;

/**
 * Created by Messi on 2021/5/10
 */
public interface IStorageDetail extends IService<StorageDetail> {
    StorageDetail findByProductMaterialNo(String productNo);

    Integer DeleteStorage(String materialNo);
}
