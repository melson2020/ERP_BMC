package com.melson.webserver.dict.service.impl;

import com.melson.webserver.dict.dao.IProduceTechRepository;
import com.melson.webserver.dict.entity.ProduceTech;
import com.melson.webserver.dict.service.IProduceTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
@Service
public class ProduceTechServiceImpl implements IProduceTechService {
    @Autowired
    private IProduceTechRepository produceTechRepository;

    public ProduceTech SaveOne(ProduceTech produceTech){
        return produceTechRepository.save(produceTech);
    }

    @Override
    public void DeleteOne(Integer id) {
        produceTechRepository.deleteById(id);
    }

    @Override
    public List<ProduceTech> findAll() {
        return produceTechRepository.findAll();
    }
}
