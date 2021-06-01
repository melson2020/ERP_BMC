package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.BomLogsDetail;
import com.melson.webserver.dict.service.IBomLogsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/5/31
 */
@Service
public class IBomLogsDetailImpl extends AbstractService<BomLogsDetail> implements IBomLogsDetail {
    @Override
    public JpaRepository<BomLogsDetail, String> getRepository() {
        return null;
    }
}
