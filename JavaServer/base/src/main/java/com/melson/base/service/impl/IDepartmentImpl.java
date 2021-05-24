package com.melson.base.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.dao.IDepartmentDao;
import com.melson.base.entity.Department;
import com.melson.base.service.IDepartment;
import com.melson.base.service.ISysSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Messi on 2021/5/23
 */
@Service
public class IDepartmentImpl extends AbstractService<Department> implements IDepartment {
    private final IDepartmentDao departmentDao;
    private final ISysSequence sysSequenceService;

    public IDepartmentImpl(IDepartmentDao departmentDao, ISysSequence sysSequenceService) {
        this.departmentDao = departmentDao;
        this.sysSequenceService = sysSequenceService;
    }

    @Override
    public JpaRepository<Department, String> getRepository() {
        return null;
    }

    @Override
    public List<Department> GetDepartmentList() {
        return departmentDao.findAll();
    }

    @Override
    public Result SaveAndUpdate(Department department) {
        Result result = new Result();
        Department checkExist=departmentDao.findByDepartmentName(department.getDepartmentName());
        if(checkExist!=null){
            if(department.getId()==checkExist.getId()){
                Department saved=departmentDao.save(department);
                if(saved==null){
                    result.setResultStatus(-1);
                    result.setMessage("保存失败！");
                }else {
                    result.setData(saved);
                }
            }
            else
            {
                result.setResultStatus(-1);
                result.setMessage("已经存在此类别名称或联系管理员！");
            }
        }
        else
        {
            if(department.getId()==null) {
                department.setDepartmentCode(sysSequenceService.GenerateCode(Department.DEPARTMENT_NO_CHAR));
            }
            Department saved=departmentDao.save(department);
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            }else {
                result.setData(saved);
            }
        }
        return result;
    }

    @Override
    public Department Query(Integer id) {
        return departmentDao.findByDepartmentId(id);
    }

    @Override
    @Transactional
    public Integer UpdateDepartmentStatus(Department department) {
        Integer count = departmentDao.updateDepartmentStatus(department.getStatus(),department.getId());
        return count;
    }
}
