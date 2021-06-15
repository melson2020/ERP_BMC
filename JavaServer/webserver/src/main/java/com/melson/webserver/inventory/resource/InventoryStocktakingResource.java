package com.melson.webserver.inventory.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.constants.SysRespCode;
import com.melson.base.utils.DateUtil;
import com.melson.webserver.inventory.entity.InventoryStocktaking;
import com.melson.webserver.inventory.service.IInventoryStocktakingService;
import com.melson.webserver.inventory.vo.InventoryStocktakingVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 盘点controller
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@RestController
@RequestMapping(value = "/inventory/stocktaking")
public class InventoryStocktakingResource extends BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(InventoryStocktakingResource.class);

    @Autowired
    private IInventoryStocktakingService inventoryStocktakingService;

    @GetMapping(value = "/list")
    public Result list(HttpServletRequest request) {
        Date endDate = DateUtil.formatTime(request.getParameter("endDate"));
        return success(inventoryStocktakingService.list(endDate));
    }

    /**
     * 根据出库单号查询出库单
     *
     * @param formNo
     * @return
     */
    @GetMapping(value = "/get")
    public Result get(String formNo) {
        return success(inventoryStocktakingService.get(formNo));
    }

    @GetMapping(value = "/uncompletedOne")
    public Result getUncompletedOne() {
        return success(inventoryStocktakingService.FindUncompletedOne());
    }


    /**
     * 创建盘点单
     *
     * @param request
     * @param inventoryStocktaking 盘点单
     * @return
     */
    @PostMapping(value = "/create")
    public Result create(HttpServletRequest request, @RequestBody InventoryStocktaking inventoryStocktaking) {
       if(inventoryStocktaking==null)return this.GenerateResult(ResultType.ParameterNeeded);
        InventoryStocktaking saved= inventoryStocktakingService.Create(inventoryStocktaking);
        if(saved==null){
            return failure(-1,"创建失败");
        }else {
            return success(saved);
        }
    }

    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody InventoryStocktaking inventoryStocktaking) {
        if(inventoryStocktaking==null)return this.GenerateResult(ResultType.ParameterNeeded);
        InventoryStocktaking saved=inventoryStocktakingService.Save(inventoryStocktaking);
        if(saved==null){
            return failure(-1,"盘点失败");
        }else {
            return success(saved);
        }
    }
}
