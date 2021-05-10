package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.StorageDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Messi on 2021/5/10
 */
public interface IStorageDetailRepository extends JpaRepository<StorageDetail,String> {
}
