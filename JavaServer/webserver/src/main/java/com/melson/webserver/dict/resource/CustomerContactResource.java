package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.CustomerContact;
import com.melson.webserver.dict.service.ICustomerContact;
import com.melson.webserver.dict.vo.CustomerContactVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        List<CustomerContactVo> contactVoList=customerContactService.getAllContact();
        Result result=new Result();
        result.setData(contactVoList);
        return result;
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result QueryContact(@RequestBody CustomerContact contact){
        CustomerContact cc=customerContactService.findContact(contact.getId());
        Result result=new Result();
        result.setData(cc);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result SaveContact(@RequestBody CustomerContact contact){
        return customerContactService.SaveAndUpdate(contact);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result DeleteContact(@RequestBody CustomerContact contact){
        Result result=new Result();
        Integer deleteCount=customerContactService.DeleteContact(contact.getId());
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }
}
