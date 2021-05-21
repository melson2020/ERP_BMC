package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.ProduceProcess;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IProduceProcess extends IService<ProduceProcess> {
    ProduceProcess SaveOne(ProduceProcess produceProcess,String userId);

    List<ProduceProcess> findAll();

    void DeleteOne(Integer id);
}
