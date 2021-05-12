package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.interceptor.RequiredPermission;
import com.melson.base.interceptor.SecurityLevel;
import com.melson.webserver.dict.entity.Material;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IMaterial;
import com.melson.webserver.dict.service.IStorageDetail;
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
@RequestMapping("/material")
public class MaterialResource extends BaseResource {
    private final IMaterial materialService;
    private final IStorageDetail storageDetailService;

    public MaterialResource(IMaterial materialService, IStorageDetail storageDetailService) {
        this.materialService = materialService;
        this.storageDetailService = storageDetailService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetMaterialList(HttpServletRequest request){
        List<Material> materials=materialService.findAllList();
        Result result=new Result();
        result.setData(materials);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result CreateMaterial(@RequestBody Material material){
        return materialService.SaveAndUpdate(material);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result DeleteMaterial(@RequestBody Material material){
        Result result=new Result();
        StorageDetail storage=storageDetailService.findByProductMaterialNo(material.getPartNo());
        if(storage!=null && storage.getCount()>0)
        {
            result.setResultStatus(-1);
            result.setMessage("库存已存在的数据不能删除！");
        }
        else
        {
            storageDetailService.DeleteStorage(material.getPartNo());
            Integer deleteCount=materialService.DeleteMaterial(material.getId());
            result.setResultStatus(deleteCount>0?1:-1);
        }
        return result;
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @RequiredPermission(SecurityLevel.Employee)
    public Result QueryMaterial(@RequestBody Material material){
        Material mat=materialService.Query(material.getPartNo());
        Result result=new Result();
        result.setData(mat);
        return result;
    }

}
