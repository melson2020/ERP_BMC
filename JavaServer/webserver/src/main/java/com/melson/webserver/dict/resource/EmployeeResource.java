package com.melson.webserver.dict.resource;

import com.melson.base.Result;
import com.melson.base.entity.Department;
import com.melson.base.interceptor.RequiredPermission;
import com.melson.base.interceptor.SecurityLevel;
import com.melson.base.service.IDepartment;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.ProductCategory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/5/24
 */
@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private final IDepartment departmentService;

    public EmployeeResource(IDepartment departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(value = "/departmentList",method = RequestMethod.GET)
    public Result GetDepartmentList(HttpServletRequest request){
        List<Department> departments=departmentService.GetDepartmentList();
        Result result=new Result();
        result.setData(departments);
        return result;
    }

    @RequestMapping(value = "/departmentSave",method = RequestMethod.POST)
    public Result SaveDepartment(@RequestBody Department department){
        return departmentService.SaveAndUpdate(department);
    }

    @RequestMapping(value = "/queryDepartment",method = RequestMethod.POST)
    public Result QueryDepartment(@RequestBody Department department){
        Department depart=departmentService.Query(department.getId());
        Result result=new Result();
        result.setData(depart);
        return result;
    }

    @RequestMapping(value = "/updateDepartmentStatus",method = RequestMethod.POST)
    public Result UpdateDepartmentStatus(@RequestBody Department department){
        Result result=new Result();
        Integer disableCount=departmentService.UpdateDepartmentStatus(department);
        result.setResultStatus(disableCount>0?1:-1);
        result.setData(disableCount);
        return result;
    }

}
