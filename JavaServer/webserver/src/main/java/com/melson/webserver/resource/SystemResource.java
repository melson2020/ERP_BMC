package com.melson.webserver.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.entity.Company;
import com.melson.base.entity.User;
import com.melson.base.service.ICompany;
import com.melson.base.service.IUser;
import com.melson.webserver.vo.RegisterInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/27
 */
@RestController
@RequestMapping(value = "/system")
public class SystemResource extends BaseResource {
    private final ICompany companyService;
    private final IUser userService;

    public SystemResource(ICompany companyService, IUser userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @RequestMapping(value = "/registerCompany", method = RequestMethod.POST)
    public Result RegisterCompany(@RequestBody RegisterInfo registerInfo, HttpServletRequest request) {
        Company company=registerInfo.getCompany();
        if (StringUtils.isEmpty(company.getPhoneNumber()) || StringUtils.isEmpty(company.getCompanyName()) || StringUtils.isEmpty(company.getCommunicateName()))
            return this.GenerateResult(ResultType.ParameterNeeded);
        Result result = companyService.RegisterCompany(company,registerInfo.getPassword());
        return result;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result SystemLogin(@RequestBody User loginUser){
        Result result=new Result();
        return result;
    }
}
