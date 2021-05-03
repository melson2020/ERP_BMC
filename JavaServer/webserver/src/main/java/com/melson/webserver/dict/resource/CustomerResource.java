package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.interceptor.RequiredPermission;
import com.melson.base.interceptor.SecurityLevel;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.service.ICustomer;
import com.melson.webserver.dict.vo.ContractVo;
import com.melson.webserver.dict.vo.CustomerVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/customer")
public class CustomerResource extends BaseResource {
    private final ICustomer customerService;

    public CustomerResource(ICustomer customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetCustomerList(HttpServletRequest request){
//        List<Customer> customers=customerService.findAll();  // 包含description
        List<Customer> customers=customerService.findAllExclude();  // 不包含description, Status="Y"
        Result result=new Result();
        result.setData(customers);
        return result;
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @RequiredPermission(SecurityLevel.Employee)
    public Result QueryCustomer(@RequestBody Customer customer){
        Optional<Customer> cus=customerService.Query(customer);
        Result result=new Result();
        result.setData(cus);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiredPermission(SecurityLevel.Employee)
    public Result CreateCustomer(@RequestBody Customer customer){
        return customerService.SaveAndUpdate(customer);
    }

    @RequestMapping(value = "/disable",method = RequestMethod.POST)
    public Result DisableCustomer(@RequestBody Customer customer){
        Result result=new Result();
        Integer disableCount=customerService.DisableCustomer(customer);
        result.setResultStatus(disableCount>0?1:-1);
        result.setData(disableCount);
        return result;
    }

    @RequestMapping(value = "/listVo",method = RequestMethod.GET)
    public Result GetCustomerVoList(HttpServletRequest request){
        List<CustomerVo> customerVos=customerService.findVoAll();
        Result result=new Result();
        result.setData(customerVos);
        return result;
    }

    @RequestMapping(value = "/queryCustomer",method = RequestMethod.GET)
    public Result QueryCustomer(HttpServletRequest request){
        String customerNo=request.getParameter("customerNo");
        if (StringUtils.isEmpty(customerNo)) return this.GenerateResult(ResultType.ParameterNeeded);
        Result result=new Result();
        ContractVo contractVo=customerService.findCustomerVoByCustomerNo(customerNo);
        result.setData(contractVo);
        return result;
    }

}
