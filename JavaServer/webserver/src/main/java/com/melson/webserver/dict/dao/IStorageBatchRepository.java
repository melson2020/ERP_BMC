package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/10
 */
@Repository
public interface IStorageBatchRepository extends JpaRepository<StorageBatch, String> {
}
