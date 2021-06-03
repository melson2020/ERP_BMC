package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author Nelson
 * @Description 采购计划表
 * @Date 2021/5/6
 */
@Entity
@Table(name = "purchase_plan")
public class PurchasePlan {
    public static final String PURCHASE_NO_CHAR="PUR";

    public static final String PURCHASE_TYPE_ORDER="ORDER";           //生产采购
    public static final String PURCHASE_TYPE_PLAN="PLAN";             //计划采购
    public static final String PURCHASE_TYPE_INDIRECT="INDIRECT";     //间接采购

    public static final String PURCHASE_STATE_CREATE="CREATE";      //待审核
    public static final String PURCHASE_STATE_APPROVE="APPROVE";    //已批准
    public static final String PURCHASE_STATE_BUYING="BUYING";      //采购中
    public static final String PURCHASE_STATE_COMPLETE="COMPLETE";  //已入库
    public static final String PURCHASE_STATE_CANCEL="CANCEL";      //取消
    public static final String PURCHASE_STATE_REJECT="REJECT";      //否决


    public static final String PURCHASE_ACCOUNT_MRP="MRP";          //生产环节生成的采购订单，默认账号为 MRP

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderFormId;
    private String orderFormNo;
    private String customerNo;
    private String customerName;
    private String contractNo;
    private String requester;     // 1:MRP , 系统生成的采购单  ；  2：根据页面获取系统的用户名，
    private String planNo;
    // 类型 标明 生产源头 如:订单生成,生产生成,间接采购等
    private String type;
    // 状态值  如;待审核（CREATE），已批准(APPROVE)，采购中(BUYING)，已入库(COMPLETE)
    private String state;
    private Date createDate;
    private Integer createBy;
    private String description;


    @Transient
    private List<PurchaseDetail> purchaseDetailList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Integer orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getOrderFormNo() {
        return orderFormNo;
    }

    public void setOrderFormNo(String orderFormNo) {
        this.orderFormNo = orderFormNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public List<PurchaseDetail> getPurchaseDetailList() {
        return purchaseDetailList;
    }

    public void setPurchaseDetailList(List<PurchaseDetail> purchaseDetailList) {
        this.purchaseDetailList = purchaseDetailList;
    }
}
