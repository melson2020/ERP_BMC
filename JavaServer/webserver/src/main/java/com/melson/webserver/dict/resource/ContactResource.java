package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IContact;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/Contact")
public class ContactResource extends BaseResource {
    private final IContact contactService;

    public ContactResource(IContact contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetContactList(HttpServletRequest request){
        return null;
    }
}
