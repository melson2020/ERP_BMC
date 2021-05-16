package com.melson.webserver.inventory.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
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

    /**
     * 盘点
     *
     * @param request
     * @param vo
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody InventoryStocktakingVo vo) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        if (vo == null) {
            return failure(SysRespCode.STOCKTAKING_SAVE_IS_NULL, "待盘点信息为空");
        }
        InventoryStocktaking form = inventoryStocktakingService.save(vo, userId);
        if (form == null) {
            return failure(SysRespCode.STOCKTAKING_SAVE_FAIL, "盘点失败");
        }
        logger.info("用户[{}]盘点[{}]成功", userId, form.getFormNo());
        return success(form.getFormNo());
    }
}
