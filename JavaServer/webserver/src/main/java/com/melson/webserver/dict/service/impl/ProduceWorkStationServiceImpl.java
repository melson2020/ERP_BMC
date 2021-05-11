package com.melson.webserver.dict.service.impl;

import com.melson.webserver.dict.dao.IProduceWorkStationRepository;
import com.melson.webserver.dict.entity.ProduceWorkStation;
import com.melson.webserver.dict.service.IProduceWorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/11
 */
@Service
public class ProduceWorkStationServiceImpl implements IProduceWorkStationService {
    @Autowired
    private IProduceWorkStationRepository workStationRepository;

    @Override
    public List<ProduceWorkStation> FindListByLineId(Integer lineId) {
        return workStationRepository.findByProduceLineId(lineId);
    }

    @Override
    public void DeleteOne(Integer id) {
        workStationRepository.deleteById(id);
    }
}
