package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IProductBom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/productBom")
public class ProductBomResource extends BaseResource {
    private final IProductBom productBomService;

    public ProductBomResource(IProductBom productBomService) {
        this.productBomService = productBomService;
    }

    /**
     * 获取产品bom列表
     * @param productNo
     * @return
     */
    @GetMapping("/pBomList")
    public Result FindProductBomList(String productNo){
        return  success(productBomService.findProductBoms(productNo));
    }


}
