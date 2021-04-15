package com.melson.webserver.contract.service.impl;

import com.melson.webserver.contract.service.IContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合同接口实现类
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IContractServiceImpl implements IContractService {

}
