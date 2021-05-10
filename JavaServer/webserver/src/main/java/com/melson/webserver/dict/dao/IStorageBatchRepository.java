package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageBatch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Messi on 2021/5/10
 */
public interface IStorageBatchRepository extends JpaRepository<StorageBatch, String> {
}
