package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.ICustomerContact;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Messi on 2021/4/26
 */

@RestController
@RequestMapping("/customerContact")
public class CustomerContactResource extends BaseResource {
    private final ICustomerContact customerContactService;

    public CustomerContactResource(ICustomerContact customerContactService) {
        this.customerContactService = customerContactService;
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetCustomerContactList(HttpServletRequest request){
        return null;
    }
}
