package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.webserver.dict.service.*;
import com.melson.webserver.dict.vo.ContractProductVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@RestController
@RequestMapping("/common")
public class CommonResource extends BaseResource {
    private final ICustomer customerService;
    private final IProduct productService;
    private final IBoms bomsSerivce;
    private final IContact contactService;
    private final ICustomerContact customerContactService;
    private final IMaterial materialService;
    private final IProductBom productBomService;
    private final IProductCategory productCategoryService;
    private final IProduceProcess produceProcessService;


    public CommonResource(ICustomer customerService, IProduct productService,  IBoms bomsSerivce, IContact contactService, ICustomerContact customerContactService, IMaterial materialService, IProductBom productBomService, IProductCategory productCategoryService, IProduceProcess produceProcessService) {
        this.customerService = customerService;
        this.productService = productService;
        this.bomsSerivce = bomsSerivce;
        this.contactService = contactService;
        this.customerContactService = customerContactService;
        this.materialService = materialService;
        this.productBomService = productBomService;
        this.productCategoryService = productCategoryService;
        this.produceProcessService = produceProcessService;
    }

    @RequestMapping(value = "/initialContract",method = RequestMethod.GET)
    public Result GetContractLProdctVo(HttpServletRequest request){
        String searchValue=request.getParameter("searchValue");
        String customerNo=request.getParameter("customerNo");
        if (StringUtils.isEmpty(customerNo)) return this.GenerateResult(ResultType.ParameterNeeded);
        Result result=new Result();
        List<ContractProductVo> contractProductVos=productService.queryContractProducts(customerNo,searchValue);
        result.setData(contractProductVos);
        return result;
    }

}
