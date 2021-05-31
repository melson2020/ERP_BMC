package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.BomLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/31
 */
@Repository
public interface IBomLogsRepository extends JpaRepository<BomLogs,String> {
}
