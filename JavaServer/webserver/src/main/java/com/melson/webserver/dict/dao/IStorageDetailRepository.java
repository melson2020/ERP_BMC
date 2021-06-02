package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/10
 */
@Repository
public interface IStorageDetailRepository extends JpaRepository<StorageDetail,String>, JpaSpecificationExecutor<StorageDetail> {

    StorageDetail findByMaterialNo(String productNo);

    @Modifying
    @Query(value = "delete from storage_detail where materialNo=?1",nativeQuery = true)
    Integer deleteByMaterialNo(String materialNo);

    StorageDetail findByProductIdAndUnit(Integer productId,String unit);
}
