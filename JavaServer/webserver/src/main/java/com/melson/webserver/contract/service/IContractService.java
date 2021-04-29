package com.melson.webserver.contract.service;

import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.vo.ContractInfoVo;
import com.melson.webserver.contract.vo.ContractShowVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 合同接口
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
public interface IContractService {

    /**
     * 根据合同类型+合同号+客户名称查询合同集合
     *
     * @param type
     * @return
     */
    List<ContractShowVo> intentionList(String type);

    /**
     * 根据合同id查询合同对象
     *
     * @param id
     * @return
     */
    ContractInfoVo get(Integer id);

    /**
     * 保存合同
     *
     * @param request
     * @param contractType
     * @param vo
     * @param userId
     * @return
     */
    Contract save(HttpServletRequest request, String contractType, ContractInfoVo vo, int userId);

    /**
     * 作废合同
     *
     * @param id
     * @return
     */
    Contract invalid(Integer id);

    /**
     * 转为正式合同
     *
     * @param id
     * @param userId
     * @return
     */
    Contract approve(Integer id, int userId);


    /**
     * 在正式合同中查找list
     *
     * @param kvMap
     *
     * @return
     */
    List<ContractShowVo> findFormalList(Map<String,String[]> kvMap);
}
