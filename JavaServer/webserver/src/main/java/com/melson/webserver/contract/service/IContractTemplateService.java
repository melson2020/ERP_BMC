package com.melson.webserver.contract.service;

import com.melson.webserver.contract.entity.ContractTemplate;
import com.melson.webserver.contract.vo.ContractTemplateVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/22
 */

public interface IContractTemplateService {
    ContractTemplateVo findContractTemplateByType(String type);
}
