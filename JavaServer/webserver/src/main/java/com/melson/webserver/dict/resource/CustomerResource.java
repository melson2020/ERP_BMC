package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.service.ICustomer;
import com.melson.webserver.dict.vo.ContractVo;
import com.melson.webserver.dict.vo.CustomerVo;
import javafx.scene.chart.ValueAxis;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        List<Customer> customers=customerService.findAll();
        Result result=new Result();
        result.setData(customers);
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
