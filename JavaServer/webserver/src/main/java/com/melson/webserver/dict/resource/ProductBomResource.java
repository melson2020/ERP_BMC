package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.entity.ProductBom;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.service.IProductBom;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/productBom")
public class ProductBomResource extends BaseResource {
    private final IProductBom productBomService;
    private final IBoms bomsService;

    public ProductBomResource(IProductBom productBomService, IBoms bomsService) {
        this.productBomService = productBomService;
        this.bomsService = bomsService;
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

    @RequestMapping(value = "/getProductBomList",method = RequestMethod.GET)
    public Result GetProductBomList(HttpServletRequest request){
        Result result=new Result();
        List<ProductBom> productBomList = productBomService.GetProductBomList();
        result.setData(productBomList);
        return result;
    }

    @RequestMapping(value = "/saveProductBom",method = RequestMethod.POST)
    public Result CreateProductBom(@RequestBody ProductBom pb){
        return productBomService.SaveAndUpdate(pb);
    }

    @RequestMapping(value = "/updateProductBom",method = RequestMethod.POST)
    public Result UpdateProductBom(@RequestBody ProductBom pb){
        Result result=new Result();
        ProductBom bom=productBomService.UpdateProductBom(pb);
       if(bom!=null){
           return success(bom);
       }else {
           return failure(-1,"更新失败");
       }
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result QueryProduct(@RequestBody ProductBom pb){
        ProductBom productBom=productBomService.Query(pb.getBomNo());
        Result result=new Result();
        result.setData(productBom);
        return result;
    }

    @RequestMapping(value = "/ProductBomsDetail",method = RequestMethod.POST)
    public Result QueryProductBomsDetailList(@RequestBody ProductBom pb){
        ProductBom pro=productBomService.QueryProductBomsDetailList(pb.getBomNo());
        Result result=new Result();
        result.setData(pro);
        return result;
    }

    @RequestMapping(value = "/detailBomsSave",method = RequestMethod.POST)
    public Result SaveDetailBom(@RequestBody Boms bom){
        return bomsService.SaveAndUpdate(bom);
    }

    @RequestMapping(value = "/detailBomsDelete",method = RequestMethod.POST)
    public Result DeleteDetailBom(@RequestBody Boms bom){
        Result result=new Result();
        Integer deleteCount=bomsService.DeleteUserGroup(bom.getId());
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }

}
