package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IProduceProcess;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Messi on 2021/4/26
 */

@RestController
@RequestMapping("/productProcess")
public class ProductProcessResource extends BaseResource {
    private final IProduceProcess produceProcessService;

    public ProductProcessResource(IProduceProcess produceProcessService) {
        this.produceProcessService = produceProcessService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetProductProcessList(HttpServletRequest request){
        return null;
    }
}
