package com.melson.base.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.base.entity.Department;
import com.melson.base.entity.Role;

import java.util.List;

/**
 * Created by Messi on 2021/5/23
 */
public interface IDepartment extends IService<Department> {
    List<Department> GetDepartmentList();

    Result SaveAndUpdate(Department department);

    Department Query(Integer id);

    Integer UpdateDepartmentStatus(Department department);

}
