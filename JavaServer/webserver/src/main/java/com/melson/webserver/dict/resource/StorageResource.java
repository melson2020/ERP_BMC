package com.melson.webserver.dict.resource;

import com.melson.base.Result;
import com.melson.webserver.dict.entity.ProductCategory;
import com.melson.webserver.dict.entity.StorageAreaLocation;
import com.melson.webserver.dict.service.IStorageAreaLocation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/5/10
 */

@RestController
@RequestMapping("/storage")
public class StorageResource {
    private final IStorageAreaLocation storageAreaLocationService;

    public StorageResource(IStorageAreaLocation storageAreaLocationService) {
        this.storageAreaLocationService = storageAreaLocationService;
    }

    @RequestMapping(value = "/storageAreaLocationList",method = RequestMethod.GET)
    public Result GetStorageAreaLocationList(HttpServletRequest request){
        List<StorageAreaLocation> storageAreaLocations=storageAreaLocationService.findAll();
        Result result=new Result();
        result.setData(storageAreaLocations);
        return result;
    }
}
