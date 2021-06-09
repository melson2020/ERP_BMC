package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/5/10
 */
@Repository
public interface IStorageBatchRepository extends JpaRepository<StorageBatch, String> {
    List<StorageBatch> findByMaterialIdInAndFinished(Set<Integer> productId,Integer finish);
}
