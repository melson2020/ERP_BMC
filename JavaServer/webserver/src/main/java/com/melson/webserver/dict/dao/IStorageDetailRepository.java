package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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

    List<StorageDetail> findByProductIdIn(Set<Integer> productIds);

    List<StorageDetail> findByProductId(Integer productId);

    @Query(nativeQuery = true,value = "SELECT sd.productId as materialId,sd.count as materialCount,sd.unit as materialUnit,sb.batchNo,sb.count as batchCount,sb.countUnit as batchUnit,sd.storageCode FROM `storage_detail` sd RIGHT JOIN storage_batch sb ON sd.productId=sb.materialId WHERE sb.finished=0 and sd.count>0 and sd.materialNo in ?1")
    List<Object[]> findStorageDetailBatchInfo(Set<String> materialNos);

}
