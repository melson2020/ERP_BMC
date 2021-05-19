package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.Supply;
import com.melson.webserver.dict.service.ISupply;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/5/13
 */
@RestController
@RequestMapping("/supply")
public class SupplyResource extends BaseResource {
    private final ISupply supplyService;

    public SupplyResource(ISupply supplyService) {
        this.supplyService = supplyService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result GetSupplyList(HttpServletRequest request){
        List<Supply> supplies=supplyService.findAll();
        Result result=new Result();
        result.setData(supplies);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result SaveSupply(@RequestBody Supply supply){
        return supplyService.SaveAndUpdate(supply);
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result QuerySupply(@RequestBody Supply supply){
        Supply sup=supplyService.Query(supply.getId());
        Result result=new Result();
        result.setData(sup);
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result DeleteSupply(@RequestBody Supply supply){
        Result result=new Result();
        Integer deleteCount=supplyService.DeleteSupply(supply.getId());
        result.setResultStatus(deleteCount>0?1:-1);
        result.setData(deleteCount);
        return result;
    }



}
