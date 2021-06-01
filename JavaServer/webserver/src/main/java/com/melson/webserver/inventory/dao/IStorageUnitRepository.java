package com.melson.webserver.inventory.dao;

import com.melson.webserver.inventory.entity.StorageUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/1
 */
@Repository
public interface IStorageUnitRepository extends JpaRepository<StorageUnit,Integer> {
}
