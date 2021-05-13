package com.melson.webserver.dict.service;

import com.melson.webserver.dict.entity.ProduceLine;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
public interface IProduceLineService {
    ProduceLine SaveOne(ProduceLine line);
    List<ProduceLine> FindAll();
    void DeleteOne(Integer id);
}
