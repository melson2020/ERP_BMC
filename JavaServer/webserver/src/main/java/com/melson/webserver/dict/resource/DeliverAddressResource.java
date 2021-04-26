package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IDeliverAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/deliverAddress")
public class DeliverAddressResource extends BaseResource {
    private final IDeliverAddress deliverAddressService;

    public DeliverAddressResource(IDeliverAddress deliverAddressService) {
        this.deliverAddressService = deliverAddressService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetDeliverAddressList(HttpServletRequest request){
        return null;
    }
}
