package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IMaterial;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/material")
public class MaterialResource extends BaseResource {
    private final IMaterial materialService;

    public MaterialResource(IMaterial materialService) {
        this.materialService = materialService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetMaterialList(HttpServletRequest request){
        return null;
    }
}
