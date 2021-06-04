package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.StorageUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/1
 */
@Repository
public interface IStorageUnitRepository extends JpaRepository<StorageUnit,Integer> {
    List<StorageUnit> findByProductId(Integer productId);
    List<StorageUnit> findByProductIdAndConvertUnit(Integer productId,String convertUit);
    List<StorageUnit> findByProductIdAndPackageUnit(Integer productId,String packageUnit);
}
