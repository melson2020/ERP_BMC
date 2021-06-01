package com.melson.webserver.inventory.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IStorageDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/1
 */
@RestController
@RequestMapping(value = "/storageDetail")
public class StorageDetailResource extends BaseResource {
    @Autowired
    private IStorageDetail storageDetailService;

    @GetMapping(value = "/detailList")
    public Result FindAllStorageList(String searchValue,Integer page, Integer pageSize){
        return success(storageDetailService.FindAllStorageList(searchValue,page,pageSize));
    }
}
