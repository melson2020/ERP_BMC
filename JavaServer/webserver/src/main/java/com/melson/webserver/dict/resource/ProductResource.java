package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.service.IProduct;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/product")
public class ProductResource extends BaseResource {
    private final IProduct productService;

    public ProductResource(IProduct productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetProductList(HttpServletRequest request){
        List<Product> productList=productService.findAllProduct();
        Result result=new Result();
        result.setData(productList);
        return result;
    }
}
