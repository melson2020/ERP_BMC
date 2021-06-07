package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.entity.Department;
import com.melson.base.entity.Role;
import com.melson.base.entity.User;
import com.melson.base.service.IDepartment;
import com.melson.base.service.IRole;
import com.melson.base.service.IUser;
import com.melson.webserver.dict.entity.UserGroup;
import com.melson.webserver.dict.entity.WorkGroup;
import com.melson.webserver.dict.service.IWorkGroup;
import com.melson.webserver.dict.service.IUserGroup;
import com.melson.webserver.dict.vo.UserPassVo;
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
public class EmployeeResource extends BaseResource {
    private final IDepartment departmentService;
    private final IRole roleService;
    private final IUser userService;
    private final IUserGroup userGroupService;
    private final IWorkGroup workGroupService;

    public EmployeeResource(IDepartment departmentService, IRole roleService, IUser userService, IUserGroup userGroupService, IWorkGroup workGroupService) {
        this.departmentService = departmentService;
        this.roleService = roleService;
        this.userService = userService;
        this.userGroupService = userGroupService;
        this.workGroupService = workGroupService;
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

    @RequestMapping(value = "/roleList",method = RequestMethod.GET)
    public Result GetRoleList(HttpServletRequest request){
        List<Role> roles=roleService.GetRoleList();
        Result result=new Result();
        result.setData(roles);
        return result;
    }

    @RequestMapping(value = "/employeeList",method = RequestMethod.GET)
    public Result GetEmployeeList(HttpServletRequest request){
        String userType = request.getParameter("userType");
        List<User> employeeList=userService.GetEmployeeList(userType);
        Result result=new Result();
        result.setData(employeeList);
        return result;
    }


    @RequestMapping(value = "/employeeSave",method = RequestMethod.POST)
    public Result SaveUser(@RequestBody User user){
        return userService.SaveAndUpdate(user);
    }

    @RequestMapping(value = "/queryEmployee",method = RequestMethod.POST)
    public Result QueryEmployee(@RequestBody User user){
        User employee=userService.QueryEmployee(user.getId());
        Result result=new Result();
        result.setData(employee);
        return result;
    }

    @RequestMapping(value = "/updateEmployeeStatus",method = RequestMethod.POST)
    public Result UpdateUserStatus(@RequestBody User user){
        Result result=new Result();
        Integer disableCount=userService.UpdateUserStatus(user);
        result.setResultStatus(disableCount>0?1:-1);
        result.setData(disableCount);
        return result;
    }

    @RequestMapping(value = "/workGroupList",method = RequestMethod.GET)
    public Result GetWorkGroupList(HttpServletRequest request){
        List<WorkGroup> workGroups=workGroupService.GetWorkGroupList();
        Result result=new Result();
        result.setData(workGroups);
        return result;
    }

    @RequestMapping(value = "/workGroupSave",method = RequestMethod.POST)
    public Result SaveWorkGroup(@RequestBody WorkGroup wg){
        return workGroupService.SaveAndUpdate(wg);
    }

    @RequestMapping(value = "/queryWorkGroup",method = RequestMethod.POST)
    public Result QueryWorkGroup(@RequestBody WorkGroup wg){
        WorkGroup workGroup=workGroupService.Query(wg.getId());
        Result result=new Result();
        result.setData(workGroup);
        return result;
    }

    @RequestMapping(value = "/deleteWorkGroup",method = RequestMethod.POST)
    public Result DeleteWorkGroup(@RequestBody WorkGroup wg){
        Result result=new Result();
        Integer deleteCount=workGroupService.DeleteWorkGroup(wg.getId());
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }

    @RequestMapping(value = "/userGroupSave",method = RequestMethod.POST)
    public Result SaveUserGroup(@RequestBody UserGroup ug){
        return userGroupService.SaveAndUpdate(ug);
    }

    @RequestMapping(value = "/deleteUserGroup",method = RequestMethod.POST)
    public Result DeleteUserGroup(@RequestBody UserGroup ug){
        Result result=new Result();
        Integer deleteCount=userGroupService.DeleteUserGroup(ug.getId());
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }

}
