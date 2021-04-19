package com.melson.webserver.contract.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.constants.SysRespCode;
import com.melson.webserver.contract.entity.Contract;
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
     * @param contractNo
     * @param orgName
     * @return
     */
    @GetMapping(value = "/list")
    public Result list(String contractNo, String orgName) {
        return success(contractService.list(getContractType(), contractNo, orgName));
    }

    /**
     * 根据id获取完整合同信息,用于页面回显
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get")
    public Result get(Integer id) {
        return success(contractService.get(id));
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
        if (vo == null) {
            return failure(SysRespCode.CONTRACT_SAVE_IS_NULL, "待保存的合同信息为空");
        }
        Contract contract = contractService.save(request, getContractType(), vo, userId);
        if (contract == null) {
            return failure(SysRespCode.CONTRACT_SAVE_FAIL, "保存失败");
        }
        logger.info("用户[{}]保存合同[{}]成功", userId, contract.getId());
        return success(contract.getId());
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
        Contract contract = contractService.invalid(id);
        if (contract == null) {
            return failure(SysRespCode.CONTRACT_INVALID_FAIL, "作废失败");
        }
        logger.info("用户[{}]作废合同[{}]成功", userId, contract.getId());
        return success();
    }

    /**
     * 转为正式合同
     *
     * @param request
     * @param id      合同id
     * @return
     */
    @DeleteMapping(value = "/approve")
    public Result approve(HttpServletRequest request, Integer id) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        Contract contract = contractService.approve(id, userId);
        if (contract == null) {
            return failure(SysRespCode.CONTRACT_APPROVE_FAIL, "转为正式合同失败");
        }
        logger.info("用户[{}]将合同[{}]转为正式合同[{}]成功", userId, id, contract.getId());
        return success(contract.getId());
    }
}
