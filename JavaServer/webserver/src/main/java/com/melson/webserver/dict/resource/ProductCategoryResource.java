package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IProductCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
        return null;
    }
}
