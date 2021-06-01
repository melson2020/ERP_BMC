package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.BomLogs;
import com.melson.webserver.dict.service.IBomLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/5/31
 */
@Service
public class IBomLogsImpl extends AbstractService<BomLogs> implements IBomLogs {
    @Override
    public JpaRepository<BomLogs, String> getRepository() {
        return null;
    }
}
