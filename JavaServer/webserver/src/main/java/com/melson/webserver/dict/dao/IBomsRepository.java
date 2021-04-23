package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Boms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface IBomsRepository extends JpaRepository <Boms,Integer>{

}
