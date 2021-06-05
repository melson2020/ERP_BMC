package com.melson.webserver.inventory.service;

import com.melson.base.Result;
import com.melson.webserver.inventory.entity.StorageUnit;
import com.melson.webserver.inventory.vo.StorageUnitVo;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/1
 */
public interface IStorageUnitService {
    StorageUnitVo FindStorageUnitListByProductId(Integer productId);
    StorageUnit SaveOne(StorageUnit unit);
    Result DeleteOne(StorageUnit unit);
    List<StorageUnit> FindPackageUnitList(Integer productId,String convertUnit);
    List<StorageUnit> FindUnPackageUnitList(Integer productId,String packageUnit);
}
