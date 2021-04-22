package com.melson.webserver.contract.service.impl;

import com.melson.webserver.contract.dao.IContractTemplateRepository;
import com.melson.webserver.contract.entity.ContractTemplate;
import com.melson.webserver.contract.service.IContractTemplateService;
import com.melson.webserver.contract.vo.ContractTemplateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/22
 */
@Service
public class ContractTemplateServiceImpl implements IContractTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(ContractTemplateServiceImpl.class);
    @Autowired
    private IContractTemplateRepository contractTemplateRepository;

    @Override
    public ContractTemplateVo findContractTemplateByType(String type) {
        List<ContractTemplate> templateList=contractTemplateRepository.findByContractType(type);
        ContractTemplateVo vo=new ContractTemplateVo();
        List<ContractTemplate> details=new ArrayList<>();
        List<ContractTemplate> remarks=new ArrayList<>();
        for (ContractTemplate template:templateList){
            if(template.getCode().equals(ContractTemplate.REMARKS)){
                remarks.add(template);
            }else if(template.getCode().equals(ContractTemplate.DETAILS)){
                details.add(template);
            }else if(template.getCode().equals(ContractTemplate.CONTENT)){
                vo.setContent(template);
            }else {
                vo.setDescription(template);
            }
        }
        vo.setDetailList(details);
        vo.setRemarkList(remarks);
        return  vo;
    }
}
