package com.melson.webserver.produce.service.impl;

import com.melson.webserver.produce.dao.IProducePlanWorkStationRepository;
import com.melson.webserver.produce.entity.ProducePlanWorkStation;
import com.melson.webserver.produce.service.IProducePlanWorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/13
 */
@Service
public class ProducePlanWorkStationServiceImpl implements IProducePlanWorkStationService {
    @Autowired
    private IProducePlanWorkStationRepository producePlanWorkStationRepository;
    @Override
    public List<ProducePlanWorkStation> SaveList(List<ProducePlanWorkStation> workStations) {
        if(workStations==null||workStations.size()<=0)return null;
        return producePlanWorkStationRepository.saveAll(workStations);
    }

    @Override
    public void DeleteOnById(Integer id) {
        producePlanWorkStationRepository.deleteById(id);
    }
}
