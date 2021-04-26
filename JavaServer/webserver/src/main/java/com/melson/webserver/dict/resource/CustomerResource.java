package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.ICustomer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
        return null;
    }

}
