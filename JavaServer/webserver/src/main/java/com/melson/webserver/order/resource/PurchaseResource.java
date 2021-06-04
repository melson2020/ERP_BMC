package com.melson.webserver.order.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.entity.User;
import com.melson.webserver.order.entity.PurchasePlan;
import com.melson.webserver.order.service.IPurchaseDetailService;
import com.melson.webserver.order.service.IPurchasePlanService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Messi on 2021/6/3
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseResource extends BaseResource {
    private final IPurchaseDetailService purchaseDetailService;
    private final IPurchasePlanService purchasePlanService;

    public PurchaseResource(IPurchaseDetailService purchaseDetailService, IPurchasePlanService purchasePlanService) {
        this.purchaseDetailService = purchaseDetailService;
        this.purchasePlanService = purchasePlanService;
    }

    @RequestMapping(value = "/purchaseList",method = RequestMethod.GET)
    public Result GetpurchaseList(HttpServletRequest request){
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        List<PurchasePlan> purchaseList=purchasePlanService.GetpurchaseList(userId);
        Result result=new Result();
        result.setData(purchaseList);
        return result;
    }

    @RequestMapping(value = "/purchaseSave",method = RequestMethod.POST)
    public Result SavePurchasePlan(@RequestBody PurchasePlan purchase){
        return purchasePlanService.Save(purchase);
    }

    @RequestMapping(value = "/queryPurchase",method = RequestMethod.POST)
    public Result QueryPurchasePlan(@RequestBody PurchasePlan purchase){
        PurchasePlan pr=purchasePlanService.QueryPurchasePlan(purchase.getPlanNo());
        Result result=new Result();
        result.setData(pr);
        return result;
    }

    @RequestMapping(value = "/updatePurchaseStatus",method = RequestMethod.POST)
    public Result UpdatePurchasePlanStatus(@RequestBody PurchasePlan purchase){
        Result result=new Result();
        Integer updateCount = purchasePlanService.UpdatePurchasePlanStatus(purchase);
        result.setResultStatus(updateCount>0?1:-1);
        result.setData(updateCount);
        return result;
    }


}