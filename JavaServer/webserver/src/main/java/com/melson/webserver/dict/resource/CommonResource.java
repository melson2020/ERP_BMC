package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.webserver.dict.entity.ProduceProcess;
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
    private final IProduct productService;
    private final IProduceProcess produceProcessService;

    public CommonResource(IProduct productService, IProduceProcess produceProcessService) {
        this.productService = productService;
        this.produceProcessService = produceProcessService;
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


}
