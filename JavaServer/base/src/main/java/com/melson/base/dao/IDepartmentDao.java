package com.melson.base.dao;

import com.melson.base.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/5/23
 */

@Repository
public interface IDepartmentDao extends JpaRepository<Department,String> {

    Department findByDepartmentName(String departmentName);

    @Query(value = "SELECT * FROM `department` where id=?1",nativeQuery = true)
    Department findByDepartmentId(Integer id);


    @Modifying
    @Query(value = "update department set `status`=?1 where id=?2",nativeQuery = true)
    Integer updateDepartmentStatus(String status, Integer id);
}
