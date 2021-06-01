package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.BomLogsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/31
 */
@Repository
public interface IBomLogsDetailRepository extends JpaRepository<BomLogsDetail,String> {
}
