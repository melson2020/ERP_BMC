package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.ProduceTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
@Repository
public interface IProduceTechRepository extends JpaRepository<ProduceTech,Integer> {
}
