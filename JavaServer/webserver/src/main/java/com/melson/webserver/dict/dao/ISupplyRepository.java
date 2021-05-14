package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/13
 */
@Repository
public interface ISupplyRepository extends JpaRepository<Supply,String> {
    Supply findByName(String name);

    Supply findById(Integer id);

    @Modifying
    @Query(value = "delete from supply where id=?1",nativeQuery = true)
    Integer deleteSupplyById(Integer id);
}
