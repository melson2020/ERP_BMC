package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.interceptor.RequiredPermission;
import com.melson.base.interceptor.SecurityLevel;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.ProductBom;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IProduct;
import com.melson.webserver.dict.service.IProductBom;
import com.melson.webserver.dict.service.IStorageDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/product")
public class ProductResource extends BaseResource {
    private final IProduct productService;
    private final IStorageDetail storageDetailService;
    private final IProductBom productBomService;

    public ProductResource(IProduct productService, IStorageDetail storageDetailService, IProductBom productBomService) {
        this.productService = productService;
        this.storageDetailService = storageDetailService;
        this.productBomService = productBomService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetProductList(HttpServletRequest request){
        List<Product> productList=productService.findAllProduct();
        Result result=new Result();
        result.setData(productList);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result CreateProduct(@RequestBody Product product){
        return productService.SaveAndUpdate(product);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result DeleteProduct(@RequestBody Product product){
        Result result=new Result();
        StorageDetail storage=storageDetailService.findByProductMaterialNo(product.getProductNo());
        if(storage!=null && storage.getCount()>0)
        {
            result.setResultStatus(-1);
            result.setMessage("???????????????????????????????????????");
        }
        else
        {
            storageDetailService.DeleteStorage(product.getProductNo());
            Integer deleteCount=productService.DeleteProduct(product.getId());
            result.setResultStatus(deleteCount>0?1:-1);
        }
        return result;
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @RequiredPermission(SecurityLevel.Employee)
    public Result QueryProduct(@RequestBody Product product){
        Product pro=productService.Query(product.getProductNo());
        Result result=new Result();
        result.setData(pro);
        return result;
    }

    @RequestMapping(value = "/queryProductAndBomList",method = RequestMethod.POST)
    @RequiredPermission(SecurityLevel.Employee)
    public Result QueryProductAndBomList(@RequestBody Product product){
        Product pro=productService.QueryProductAndBomList(product.getProductNo());
        Result result=new Result();
        result.setData(pro);
        return result;
    }

    @GetMapping(value = "/queryBySearchValue")
    public Result QueryBySearchValue(String searchValue){
        return success(productService.QueryBySearchValue(searchValue));
    }

}
