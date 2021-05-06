package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IBoms;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/Boms")
public class BomsResource extends BaseResource {
    private final IBoms bomsSerivce;

    public BomsResource(IBoms bomsSerivce) {
        this.bomsSerivce = bomsSerivce;
    }

    /**
     * 获取bom清单
     * @param bomNo
     * @return
     */
    @GetMapping("/list")
    public Result FindBomListWithBomNo(String bomNo){
       return success(bomsSerivce.findByBomNo(bomNo));
    }
}
