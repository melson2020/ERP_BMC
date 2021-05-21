package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.service.ISysSequence;
import com.melson.webserver.dict.dao.IProduceProcessRepository;
import com.melson.webserver.dict.entity.ProduceProcess;
import com.melson.webserver.dict.service.IProduceProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProduceProcessImpl extends AbstractService<ProduceProcess> implements IProduceProcess {
    private final IProduceProcessRepository produceProcessRepository;
    private final ISysSequence sysSequenceService;

    public IProduceProcessImpl(IProduceProcessRepository produceProcessRepository, ISysSequence sysSequenceService) {
        this.produceProcessRepository = produceProcessRepository;
        this.sysSequenceService = sysSequenceService;
    }

    @Override
    public JpaRepository getRepository() {
        return produceProcessRepository;
    }

    @Override
    public ProduceProcess SaveOne(ProduceProcess produceProcess, String userId) {
        if (StringUtils.isEmpty(produceProcess.getProcessNo())) {
            String ppNo = sysSequenceService.GenerateCode(ProduceProcess.NO_START_WITH);
            produceProcess.setProcessNo(ppNo);
            produceProcess.setCreateBy(userId);
        }
        return produceProcessRepository.save(produceProcess);
    }

    @Override
    public List<ProduceProcess> findAll() {
        return produceProcessRepository.findAll();
    }

    @Override
    public void DeleteOne(Integer id) {
        produceProcessRepository.deleteById(id);
    }
}
