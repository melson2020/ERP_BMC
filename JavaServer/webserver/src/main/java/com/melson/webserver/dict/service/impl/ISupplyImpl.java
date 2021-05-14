package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.ISupplyRepository;
import com.melson.webserver.dict.entity.Supply;
import com.melson.webserver.dict.service.ISupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/5/13
 */
@Service
public class ISupplyImpl extends AbstractService<Supply> implements ISupply {
    private final ISupplyRepository supplyRepository;

    public ISupplyImpl(ISupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @Override
    public JpaRepository<Supply, String> getRepository() {
        return supplyRepository;
    }

    @Override
    public List<Supply> findAll() {
        return supplyRepository.findAll();
    }
}
