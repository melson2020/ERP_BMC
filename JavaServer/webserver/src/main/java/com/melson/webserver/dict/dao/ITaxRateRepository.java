package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/4/26
 */
@Repository
public interface ITaxRateRepository extends JpaRepository<TaxRate,Integer> {
}
