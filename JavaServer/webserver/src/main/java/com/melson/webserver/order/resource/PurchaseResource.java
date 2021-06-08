package com.melson.webserver.order.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.order.entity.PurchaseDetail;
import com.melson.webserver.order.entity.PurchaseOrder;
import com.melson.webserver.order.entity.PurchasePlan;
import com.melson.webserver.order.service.IPurchaseDetailService;
import com.melson.webserver.order.service.IPurchaseOrderService;
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
    private final IPurchaseOrderService purchaseOrderService;

    public PurchaseResource(IPurchaseDetailService purchaseDetailService, IPurchasePlanService purchasePlanService, IPurchaseOrderService purchaseOrderService) {
        this.purchaseDetailService = purchaseDetailService;
        this.purchasePlanService = purchasePlanService;
        this.purchaseOrderService = purchaseOrderService;
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

    @RequestMapping(value = "/deletePurchase",method = RequestMethod.POST)
    public Result DeletePurchase(@RequestBody PurchasePlan purchase){
        Result result=new Result();
        Integer deleteCount=purchasePlanService.DeletePurchase(purchase);
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }

    @RequestMapping(value = "/deletePurchaseDetail",method = RequestMethod.POST)
    public Result DeletePurchase(@RequestBody PurchaseDetail pd){
        Result result=new Result();
        Integer deleteCount=purchaseDetailService.DeletePurchase(pd);
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }


    @RequestMapping(value = "/underApprovePurchaseList",method = RequestMethod.GET)
    public Result GetUnderApprovePurchaseList(HttpServletRequest request){
        String state = request.getParameter("state");
        List<PurchasePlan> purchaseList=purchasePlanService.GetUnderApprovePurchaseList(state);
        Result result=new Result();
        result.setData(purchaseList);
        return result;
    }

    @RequestMapping(value = "/allPurchaseList",method = RequestMethod.GET)
    public Result GetAllPurchaseList(HttpServletRequest request){
        List<PurchasePlan> purchaseList=purchasePlanService.findAllPR();
        Result result=new Result();
        result.setData(purchaseList);
        return result;
    }

    @RequestMapping(value = "/purchaseApprove",method = RequestMethod.POST)
    public Result ApprovePurchasePlan(@RequestBody PurchasePlan purchase){
        return purchasePlanService.ApprovePurchasePlan(purchase);
    }

    @RequestMapping(value = "/rejectPurchase",method = RequestMethod.POST)
    public Result RejectPurchase(@RequestBody PurchasePlan purchase){
        Result result=new Result();
        purchase.setState(PurchasePlan.PURCHASE_STATE_REJECT);
        Integer deleteCount=purchasePlanService.RejectPurchase(purchase);
        result.setResultStatus(deleteCount>0?1:-1);
        return result;
    }

    @RequestMapping(value = "/approvedPurchaseDetailList",method = RequestMethod.GET)
    public Result GetAllApprovedPurchaseDetailList(HttpServletRequest request){
        String state = request.getParameter("state");
        List<PurchaseDetail> prDetailList=purchaseDetailService.GetAllApprovedPurchaseDetailList(state);
        Result result=new Result();
        result.setData(prDetailList);
        return result;
    }

    @RequestMapping(value = "/createdPoList",method = RequestMethod.GET)
    public Result GetAllCreatedPoList(HttpServletRequest request){
        String state = request.getParameter("state");
        List<PurchaseOrder> poList=purchaseOrderService.GetAllCreatedPoList(state);
        Result result=new Result();
        result.setData(poList);
        return result;
    }

    @RequestMapping(value = "/poSave",method = RequestMethod.POST)
    public Result SavePO(@RequestBody PurchaseOrder po){
        return purchaseOrderService.Save(po);
    }

    @RequestMapping(value = "/queryPo",method = RequestMethod.POST)
    public Result QueryPo(@RequestBody PurchaseOrder po){
        PurchaseOrder pr=purchaseOrderService.QueryPo(po);
        Result result=new Result();
        result.setData(pr);
        return result;
    }

    @RequestMapping(value = "/allPoDetailList",method = RequestMethod.GET)
    public Result GetAllPurchaseOrderList(HttpServletRequest request){
        List<PurchaseOrder> pos=purchaseOrderService.GetAllWithCreate();
        Result result=new Result();
        result.setData(pos);
        return result;
    }


}
