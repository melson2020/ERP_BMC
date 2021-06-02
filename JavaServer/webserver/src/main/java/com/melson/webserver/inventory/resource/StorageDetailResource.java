package com.melson.webserver.inventory.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IStorageDetail;
import com.melson.webserver.inventory.entity.StorageUnit;
import com.melson.webserver.inventory.service.IStorageUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private IStorageUnitService storageUnitService;

    @GetMapping(value = "/detailList")
    public Result FindAllStorageList(String searchValue, Integer page, Integer pageSize) {
        return success(storageDetailService.FindAllStorageList(searchValue, page, pageSize));
    }

    @GetMapping(value = "/storageUnitList")
    public Result FindStorageUnitList(Integer productId) {
        return success(storageUnitService.FindStorageUnitListByProductId(productId));
    }

    @PostMapping(value = "/saveStorageUnit")
    public Result SaveStorageUnit(@RequestBody StorageUnit unit) {
        StorageUnit saved = storageUnitService.SaveOne(unit);
        if (saved != null) {
            return success(saved);
        } else {
            return failure(-1, "保存失败");
        }
    }

    @PostMapping(value = "/deleteStorageUnit")
    public Result DeleteStorageUnit(@RequestBody StorageUnit unit) {
        return storageUnitService.DeleteOne(unit);
    }
}
