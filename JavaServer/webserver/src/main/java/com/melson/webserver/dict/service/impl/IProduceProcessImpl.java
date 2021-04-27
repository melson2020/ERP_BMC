package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IProduceProcessRepository;
import com.melson.webserver.dict.entity.ProduceProcess;
import com.melson.webserver.dict.service.IProduceProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProduceProcessImpl extends AbstractService<ProduceProcess> implements IProduceProcess {
    private final IProduceProcessRepository produceProcessRepository;

    public IProduceProcessImpl(IProduceProcessRepository produceProcessRepository) {
        this.produceProcessRepository = produceProcessRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return produceProcessRepository;
    }
}
