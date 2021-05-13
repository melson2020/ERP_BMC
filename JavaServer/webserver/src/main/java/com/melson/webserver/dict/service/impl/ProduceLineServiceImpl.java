package com.melson.webserver.dict.service.impl;

import com.melson.webserver.dict.dao.IProduceLineRepository;
import com.melson.webserver.dict.dao.IProduceWorkStationRepository;
import com.melson.webserver.dict.entity.ProduceLine;
import com.melson.webserver.dict.entity.ProduceWorkStation;
import com.melson.webserver.dict.service.IProduceLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
@Service
public class ProduceLineServiceImpl implements IProduceLineService {
    @Autowired
    private IProduceLineRepository produceLineRepository;
    @Autowired
    private IProduceWorkStationRepository workStationRepository;

    @Override
    @Transactional
    public ProduceLine SaveOne(ProduceLine line) {
        ProduceLine saved = produceLineRepository.save(line);
        List<ProduceWorkStation> workStations = line.getWorkStationList();
        workStations.forEach(produceWorkStation -> {
            produceWorkStation.setProduceLineId(saved.getId());
            produceWorkStation.setProduceLineName(saved.getName());
        });
        workStationRepository.saveAll(workStations);
        return saved;
    }

    @Override
    public List<ProduceLine> FindAll() {
        return produceLineRepository.findAll();
    }

    @Override
    @Transactional
    public void DeleteOne(Integer id) {
        produceLineRepository.deleteById(id);
        workStationRepository.deleteByProduceLineId(id);
    }
}
