package com.melson.webserver.inventory.vo;

import java.util.Date;
import java.util.List;

/**
 * 出库单
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
public class InventoryOutboundVo {

    /**
     * 出库单号
     */
    private String formNo;
    /**
     * 出库类型
     */
    private String type;
    /**
     * 来源单号
     */
    private String sourceNo;
    /**
     * 描述
     */
    private String remark;

    private Date createDate;

    private Integer createUser;
    /**
     * 出库明细
     */
    private List<InventoryOutboundDetailVo> detailVoList;

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InventoryOutboundDetailVo> getDetailVoList() {
        return detailVoList;
    }

    public void setDetailVoList(List<InventoryOutboundDetailVo> detailVoList) {
        this.detailVoList = detailVoList;
    }

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
