package com.melson.webserver.produce.service;

import com.melson.webserver.produce.entity.ProducePlanWorkStation;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/13
 */
public interface IProducePlanWorkStationService {
    List<ProducePlanWorkStation> SaveList(List<ProducePlanWorkStation> workStations);
    void DeleteOnById(Integer id);
}
