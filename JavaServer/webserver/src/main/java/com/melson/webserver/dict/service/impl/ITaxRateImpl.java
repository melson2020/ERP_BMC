package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.ITaxRateRepository;
import com.melson.webserver.dict.entity.TaxRate;
import com.melson.webserver.dict.service.ITaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ITaxRateImpl extends AbstractService<TaxRate> implements ITaxRate {
    private final ITaxRateRepository taxRateRepository;

    public ITaxRateImpl(ITaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return taxRateRepository;
    }

}
