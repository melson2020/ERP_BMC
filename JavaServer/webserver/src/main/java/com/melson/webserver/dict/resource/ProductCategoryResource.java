package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.interceptor.RequiredPermission;
import com.melson.base.interceptor.SecurityLevel;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.CustomerContact;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.ProductCategory;
import com.melson.webserver.dict.service.IProductCategory;
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
@RequestMapping("/productCategory")
public class ProductCategoryResource extends BaseResource {
    private final IProductCategory productCategoryService;

    public ProductCategoryResource(IProductCategory productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetProductCategoryList(HttpServletRequest request){
        List<ProductCategory> categorys=productCategoryService.findAll();
        Result result=new Result();
        result.setData(categorys);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiredPermission(SecurityLevel.Employee)
    public Result CreateCategory(@RequestBody ProductCategory productCategory){
        return productCategoryService.SaveAndUpdate(productCategory);
    }

    @RequestMapping(value = "/disable",method = RequestMethod.POST)
    public Result DisableCategory(@RequestBody ProductCategory productCategory){
        Result result=new Result();
        Integer disableCount=productCategoryService.DisableCustomer(productCategory);
        result.setResultStatus(disableCount>0?1:-1);
        result.setData(disableCount);
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result DeleteCategory(@RequestBody ProductCategory productCategory){
        Result result=new Result();
        Integer deleteCount=productCategoryService.DeleteCategory(productCategory.getId());
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result QueryCategory(@RequestBody ProductCategory productCategory){
        ProductCategory category=productCategoryService.Query(productCategory.getCategoryId());
        Result result=new Result();
        result.setData(category);
        return result;
    }
}
