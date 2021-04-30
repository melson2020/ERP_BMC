package com.melson.webserver.order.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.constants.SysRespCode;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.service.IOrderFormService;
import com.melson.webserver.order.vo.OrderFormVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * 订单controller
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
@RestController
@RequestMapping(value = "/orderForm/manage")
public class OrderFormResource extends BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(OrderFormResource.class);

    @Autowired
    private IOrderFormService orderFormService;

    /**
     * 获取列表数据
     *
     * @param contractId
     * @return
     */
    @GetMapping(value = "/list")
    public Result list(Integer contractId) {
        return success(orderFormService.list(contractId));
    }

    @GetMapping(value = "/createdList")
    public Result createdOrderList(){
        return success(orderFormService.createdList());
    }

    /**
     * 创建订单编号
     *
     * @return
     */
    @GetMapping(value = "/createFormNo")
    public Result createFormNo(String type) {
        String formNo = MessageFormat.format("O{0}-{1}", type, UUID.randomUUID().toString());
        return success(formNo);
    }

    /**
     * 保存订单
     *
     * @param request
     * @param vo
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody OrderFormVo vo) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        if (vo == null) {
            return failure(SysRespCode.ORDER_FORM_SAVE_IS_NULL, "待保存的订单信息为空");
        }
        OrderForm orderForm = orderFormService.save(vo, userId);
        if (orderForm == null) {
            return failure(SysRespCode.ORDER_FORM_SAVE_FAIL, "保存失败");
        }
        logger.info("用户[{}]保存订单[{}]成功", userId, orderForm.getId());
        return success(orderForm.getId());
    }

    /**
     * 根据id作废合同
     *
     * @param request
     * @param id      合同id
     * @return
     */
    @DeleteMapping(value = "/invalid")
    public Result invalid(HttpServletRequest request, Integer id) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        OrderForm orderForm = orderFormService.invalid(id);
        if (orderForm == null) {
            return failure(SysRespCode.ORDER_FORM_INVALID_FAIL, "作废失败");
        }
        logger.info("用户[{}]作废订单[{}]成功", userId, orderForm.getId());
        return success();
    }
}
