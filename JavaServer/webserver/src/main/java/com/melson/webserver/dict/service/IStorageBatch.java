package com.melson.webserver.dict.service;


import com.melson.base.IService;
import com.melson.webserver.dict.entity.StorageBatch;

import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/5/10
 */
public interface IStorageBatch extends IService<StorageBatch> {
    List<StorageBatch> FindByProductIds(Set<Integer> productIds,Integer finish);
    List<StorageBatch> FindByProductId(Integer productId);
}
