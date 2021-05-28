package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/27
 */
@Repository
public interface IWorkGroupRepository extends JpaRepository<WorkGroup,String> {

    WorkGroup findByName(String name);

    @Query(value = "SELECT * FROM `work_group` where id=?1",nativeQuery = true)
    WorkGroup findByWorkGroupId(Integer id);

    @Modifying
    @Query(value = "DELETE from `work_group` WHERE id =?1",nativeQuery = true)
    Integer deleteWorkGroupById(Integer id);
}
