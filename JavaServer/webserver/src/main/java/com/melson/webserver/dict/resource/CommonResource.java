package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.entity.Company;
import com.melson.base.service.ICompany;
import com.melson.webserver.dict.entity.ProduceProcess;
import com.melson.webserver.dict.service.*;
import com.melson.webserver.dict.vo.ContractProductVo;
import com.melson.webserver.dict.vo.GroupProductVo;
import com.melson.webserver.dict.vo.ProductVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final IProduct productService;
    private final IProduceProcess produceProcessService;
    private final ICompany companyService;
    private final ITaxRate taxRateService;

    public CommonResource(IProduct productService, IProduceProcess produceProcessService, ICompany companyService, ITaxRate taxRateService) {
        this.productService = productService;
        this.produceProcessService = produceProcessService;
        this.companyService = companyService;
        this.taxRateService = taxRateService;
    }

    @RequestMapping(value = "/initialContract",method = RequestMethod.GET)
    public Result GetContractProductVo(HttpServletRequest request){
        String searchValue=request.getParameter("searchValue");
        String customerNo=request.getParameter("customerNo");
        if (StringUtils.isEmpty(customerNo)) return this.GenerateResult(ResultType.ParameterNeeded);
        Result result=new Result();
        List<ContractProductVo> contractProductVos=productService.queryContractProducts(customerNo,searchValue);
        result.setData(contractProductVos);
        return result;
    }

   @GetMapping(value = "/company")
    public Result GetCompanyInfo(){
       Company company=companyService.FindCompanyOne();
       if(company!=null){
           return success(company);
       }else {
           return failure(-1,"暂无公司信息");
       }
   }

    @RequestMapping(value = "/materialList",method = RequestMethod.GET)
    public Result GetProductVoList(HttpServletRequest request){
        String productNo=request.getParameter("productNo");
        if (StringUtils.isEmpty(productNo)) return this.GenerateResult(ResultType.ParameterNeeded);
        Result result=new Result();
        List<GroupProductVo> productVoList=productService.GetProductVoList(productNo);
        result.setData(productVoList);
        return result;
    }

    @RequestMapping(value = "/taxRateList",method = RequestMethod.GET)
    public Result GetTaxRateList(){
        return success(taxRateService.getAll());
    }
}
