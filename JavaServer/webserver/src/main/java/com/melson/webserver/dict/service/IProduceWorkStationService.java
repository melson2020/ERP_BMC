package com.melson.webserver.dict.service;

import com.melson.webserver.dict.entity.ProduceWorkStation;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/11
 */
public interface IProduceWorkStationService {
    List<ProduceWorkStation> FindListByLineId(Integer lineId);
    List<ProduceWorkStation> FindListByProcessId(Integer processId);
    void DeleteOne(Integer id);
}
