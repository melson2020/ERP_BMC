package com.melson.webserver.contract.vo;

import com.melson.webserver.contract.entity.ContractTemplate;

import java.util.List;

/**
 * 合同模板 用于填充前台合同模板
 * @Author Nelson
 * @Description
 * @Date 2021/4/22
 */
public class ContractTemplateVo {
  private   List<ContractTemplate> detailList;
  private  ContractTemplate content;
  private  ContractTemplate description;
  private List<ContractTemplate> remarkList;

    public List<ContractTemplate> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ContractTemplate> detailList) {
        this.detailList = detailList;
    }

    public ContractTemplate getContent() {
        return content;
    }

    public void setContent(ContractTemplate content) {
        this.content = content;
    }

    public ContractTemplate getDescription() {
        return description;
    }

    public void setDescription(ContractTemplate description) {
        this.description = description;
    }

    public List<ContractTemplate> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<ContractTemplate> remarkList) {
        this.remarkList = remarkList;
    }
}
