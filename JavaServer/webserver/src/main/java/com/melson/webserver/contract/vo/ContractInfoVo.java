package com.melson.webserver.contract.vo;

import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.entity.ContractOrg;
import com.melson.webserver.contract.entity.ContractStock;

import java.util.List;

/**
 * 完整合同信息
 *
 * @author wuhuan
 * @date 2021年04月16日
 */
public class ContractInfoVo {

    /**
     * 资料
     */
    private Contract contract;
    /**
     * 买方
     */
    private ContractOrg purchaser;
    /**
     * 收货单位
     */
    private ContractOrg goodReceiveInfo;
    /**
     * 合同产品集合
     */
    private List<ContractStock> productList;
    /**
     * 卖方确认
     */
    private ContractOrg vendorConfirm;
    /**
     * 买方确认
     */
    private ContractOrg purchaserConfirm;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ContractOrg getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(ContractOrg purchaser) {
        this.purchaser = purchaser;
    }

    public ContractOrg getGoodReceiveInfo() {
        return goodReceiveInfo;
    }

    public void setGoodReceiveInfo(ContractOrg goodReceiveInfo) {
        this.goodReceiveInfo = goodReceiveInfo;
    }

    public List<ContractStock> getProductList() {
        return productList;
    }

    public void setProductList(List<ContractStock> productList) {
        this.productList = productList;
    }

    public ContractOrg getVendorConfirm() {
        return vendorConfirm;
    }

    public void setVendorConfirm(ContractOrg vendorConfirm) {
        this.vendorConfirm = vendorConfirm;
    }

    public ContractOrg getPurchaserConfirm() {
        return purchaserConfirm;
    }

    public void setPurchaserConfirm(ContractOrg purchaserConfirm) {
        this.purchaserConfirm = purchaserConfirm;
    }
}
