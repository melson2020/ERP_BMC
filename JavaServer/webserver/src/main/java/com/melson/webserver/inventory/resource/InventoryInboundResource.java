package com.melson.webserver.inventory.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.constants.SysRespCode;
import com.melson.base.utils.DateUtil;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.service.IInventoryInboundService;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 入库controller
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
@RestController
@RequestMapping(value = "/inventory/inbound")
public class InventoryInboundResource extends BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(InventoryInboundResource.class);

    @Autowired
    private IInventoryInboundService inventoryInboundService;

    @GetMapping(value = "/list")
    public Result list(HttpServletRequest request) {
        Date endDate = DateUtil.formatTime(request.getParameter("endDate"));
        return success(inventoryInboundService.list(endDate));
    }

    /**
     * 根据入库单号查询入库单
     *
     * @param formNo
     * @return
     */
    @GetMapping(value = "/get")
    public Result get(String formNo) {
        return success(inventoryInboundService.get(formNo));
    }

    /**
     * 入库
     *
     * @param request
     * @param vo
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody InventoryInboundVo vo) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        if (vo == null) {
            return failure(SysRespCode.INBOUND_SAVE_IS_NULL, "待入库信息为空");
        }
        InventoryInbound form = inventoryInboundService.save(vo, userId);
        if (form == null) {
            return failure(SysRespCode.INBOUND_SAVE_FAIL, "入库失败");
        }
        logger.info("用户[{}]入库[{}]成功", userId, form.getFormNo());
        return success(form.getFormNo());
    }

    /**
     * 依据委外单/采购单/生产计划/代工详细 生产入库单
     *
     * @return
     */
    @GetMapping(value = "/createInBound")
    public Result CreateInBoundWithExistTicket(HttpServletRequest request, Integer ticketId, String ticketType) {
        if (ticketId == null || StringUtils.isEmpty(ticketType)) return GenerateResult(ResultType.ParameterNeeded);
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(token);
        InventoryInboundVo created = inventoryInboundService.createInBoundWithTicket(ticketId, ticketType, userId);
        if (created != null) {
            return success(created);
        } else {
            return failure(-1, "创建失败");
        }
    }
}
