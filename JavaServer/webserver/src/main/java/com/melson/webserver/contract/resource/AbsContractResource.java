package com.melson.webserver.contract.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.constants.SysRespCode;
import com.melson.webserver.contract.service.IContractService;
import com.melson.webserver.contract.vo.ContractInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 父类合同controller
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
public abstract class AbsContractResource extends BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(AbsContractResource.class);

    @Autowired
    private IContractService contractService;

    /**
     * 获取合同类型
     *
     * @return
     */
    protected abstract String getContractType();

    /**
     * 获取列表数据
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    public Result list(HttpServletRequest request) {
        return success(null);
    }

    /**
     * 根据id获取完整合同信息,用于页面回显
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get")
    public Result get(Integer id) {
        return success(null);
    }

    /**
     * 保存合同
     *
     * @param request
     * @param vo
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody ContractInfoVo vo) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        logger.info("用户[{}]保存合同成功", userId);
        return success();
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
        logger.info("用户[{}]作废合同成功", userId);
        return success(null);
    }
}
