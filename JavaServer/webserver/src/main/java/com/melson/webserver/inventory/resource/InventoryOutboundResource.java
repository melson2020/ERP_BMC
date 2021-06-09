package com.melson.webserver.inventory.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.constants.SysRespCode;
import com.melson.base.utils.DateUtil;
import com.melson.webserver.inventory.entity.InventoryOutbound;
import com.melson.webserver.inventory.service.IInventoryOutboundService;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.inventory.vo.InventoryOutboundVo;
import com.melson.webserver.order.service.IPickingTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 出库controller
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@RestController
@RequestMapping(value = "/inventory/outbound")
public class InventoryOutboundResource extends BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(InventoryOutboundResource.class);

    @Autowired
    private IInventoryOutboundService inventoryOutboundService;
    @Autowired
    private IPickingTicketService pickingTicketService;

    @GetMapping(value = "/list")
    public Result list(HttpServletRequest request) {
        Date endDate = DateUtil.formatTime(request.getParameter("endDate"));
        return success(inventoryOutboundService.list(endDate));
    }

    /**
     * 根据出库单号查询出库单
     *
     * @param formNo
     * @return
     */
    @GetMapping(value = "/get")
    public Result get(String formNo) {
        return success(inventoryOutboundService.get(formNo));
    }

    /**
     * 出库
     *
     * @param request
     * @param vo
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody InventoryOutboundVo vo) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        if (vo == null) {
            return failure(SysRespCode.OUTBOUND_SAVE_IS_NULL, "待出库信息为空");
        }
        InventoryOutbound form = inventoryOutboundService.save(vo, userId);
        if (form == null) {
            return failure(SysRespCode.OUTBOUND_SAVE_FAIL, "出库失败");
        }
        logger.info("用户[{}]出库[{}]成功", userId, form.getFormNo());
        return success(form.getFormNo());
    }

    @GetMapping(value = "/pickingOutBoundList")
    public Result FindPickingTicketOutBoundList() {
        return success(pickingTicketService.FindOutBoundPickingList());
    }

    @GetMapping(value = "/createOutBound")
    public Result CreateOutBoundWithExistTicket(HttpServletRequest request, Integer ticketId, String ticketType) {
        if (ticketId == null || StringUtils.isEmpty(ticketType)) return GenerateResult(ResultType.ParameterNeeded);
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(token);
//        try {
            InventoryOutboundVo vo = inventoryOutboundService.GenerateOutBoundWithExistTicket(ticketId, ticketType, userId);
            if (vo == null) {
                return failure(-1, "创建失败");
            } else {
                return success(vo);
            }
//        } catch (RuntimeException re) {
//            return failure(-1, re.getMessage());
//        }
    }
}