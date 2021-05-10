package com.melson.webserver.dict.service;

import com.melson.webserver.dict.entity.ProduceTech;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
public interface IProduceTechService {
    ProduceTech SaveOne(ProduceTech produceTech);
    void DeleteOne(Integer id);
    List<ProduceTech> findAll();
}
