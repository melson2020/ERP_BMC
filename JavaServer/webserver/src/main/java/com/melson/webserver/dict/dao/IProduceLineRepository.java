package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.ProduceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
@Repository
public interface IProduceLineRepository extends JpaRepository<ProduceLine,Integer> {
    @Query(nativeQuery = true,value = "SELECT pl.id as lineId,pl.name as lineName,pws .indexNo, pws.name as workStationName,pws.id as workStationId ,pws.employeeGroupNo FROM `produce_line` pl RIGHT JOIN produce_work_station pws on pl.id=pws.produceLineId")
    List<Object[]> findProduceLineAndWorkStation();
}
