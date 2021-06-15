package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/5/10
 */
@Repository
public interface IStorageBatchRepository extends JpaRepository<StorageBatch, String> {
    List<StorageBatch> findByMaterialIdInAndFinished(Set<Integer> productId, Integer finish);

    List<StorageBatch> findByMaterialIdAndFinishedAndCountUnitOrderByBatchNo(Integer productId, Integer finish, String unit);

    @Query(nativeQuery = true, value = "SELECT sb.materialId,sb.`name`,sb.batchNo,sb.count,sb.countUnit FROM `storage_batch` sb RIGHT JOIN product  p on sb.materialId=p.id WHERE p.storageCode=?1")
    List<Object[]> findBatchTakingInfo(String storageCode);
}
