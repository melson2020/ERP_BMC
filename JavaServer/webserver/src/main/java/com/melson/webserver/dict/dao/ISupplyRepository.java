package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/13
 */
@Repository
public interface ISupplyRepository extends JpaRepository<Supply,String> {
}
