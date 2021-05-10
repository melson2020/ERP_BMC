package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageAreaLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/10
 */
@Repository
public interface IStorageAreaLocationRepository extends JpaRepository<StorageAreaLocation,String> {
    StorageAreaLocation findByStorageCode(String storageCode);

    @Modifying
    @Query(value = "delete from storage_area_location where id =?1",nativeQuery = true)
    Integer deleteStorageAreaLocationById(Integer id);

    StorageAreaLocation findByName(String name);
}
