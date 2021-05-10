package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.interceptor.RequiredPermission;
import com.melson.base.interceptor.SecurityLevel;
import com.melson.webserver.dict.entity.ProductCategory;
import com.melson.webserver.dict.entity.StorageAreaLocation;
import com.melson.webserver.dict.service.IStorageAreaLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
public class StorageResource extends BaseResource {
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

    @RequestMapping(value = "/storageAreaLocationSave",method = RequestMethod.POST)
    public Result CreateStorageAreaLocation(@RequestBody StorageAreaLocation storageAreaLocation){
        return storageAreaLocationService.SaveAndUpdate(storageAreaLocation);
    }

    @RequestMapping(value = "/deleteStorageAreaLocation",method = RequestMethod.POST)
    public Result DeleteStorageAreaLocation(@RequestBody StorageAreaLocation storageAreaLocation){
        Result result=new Result();
        Integer deleteCount=storageAreaLocationService.DeleteStorageAreaLocationService(storageAreaLocation.getId());
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }

    @RequestMapping(value = "/queryStorageAreaLocation",method = RequestMethod.POST)
    public Result QueryStorageAreaLocation(@RequestBody StorageAreaLocation storageAreaLocation){
        StorageAreaLocation st=storageAreaLocationService.Query(storageAreaLocation.getStorageCode());
        Result result=new Result();
        result.setData(st);
        return result;
    }


}
