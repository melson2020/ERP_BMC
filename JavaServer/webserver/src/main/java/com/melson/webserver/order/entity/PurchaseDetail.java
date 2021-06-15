package com.melson.webserver.order.entity;

import com.melson.webserver.dict.entity.Supply;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Nelson
 * @Description 采购明细
 * @Date 2021/5/7
 */
@Entity
@Table(name = "purchase_detail")
public class PurchaseDetail {


//    使用 purchase_plan中的定义变量
//    public static final String PURCHASE_TYPE_ORDER="ORDER";           //生产采购
//    public static final String PURCHASE_TYPE_PLAN="PLAN";             //计划采购
//    public static final String PURCHASE_TYPE_INDIRECT="INDIRECT";     //间接采购

//    针对purchase_detail中的每一条可以设置状态
    public static final String PURCHASE_STATE_CREATE="CREATE";      //待审核
    public static final String PURCHASE_STATE_APPROVE="APPROVE";    //已批准
    public static final String PURCHASE_STATE_BUYING="BUYING";      //采购中
    public static final String PURCHASE_STATE_COMPLETE="COMPLETE";  //已入库
    public static final String PURCHASE_STATE_CANCEL="CANCEL";      //取消
    public static final String PURCHASE_STATE_REJECT="REJECT";      //否决


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 类型 标明 生产源头 如:订单生成,生产生成,间接采购等
     */
    private String type;                         // purchase_plan 传过来

    private String materialNo;

    private String materialName;

    private String specification;

    private String remark;

    private BigDecimal count;

    private String countUnit;

    private String purchasePlanNo;                // purchase_plan 传过来

    private String createEmployeeNo;

    private Integer createBy;                     // purchase_plan 传过来

    private Date createDate;

    private Integer sourceId;

    private String sourceNo;

    private String requester;                     // purchase_plan 传过来

    private Integer requesterId;

    // 状态值  如;待审核（CREATE），已批准(APPROVE)，采购中(BUYING)，已入库(COMPLETE)
    private String state;                        // purchase_plan 传过来

    private Integer supplyId;

    private BigDecimal costPrice;

    private String poNo;

    private Integer productId;

    private Integer pickingTicketId;

    @Transient
    private String supply;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
    }

    public String getPurchasePlanNo() {
        return purchasePlanNo;
    }

    public void setPurchasePlanNo(String purchasePlanNo) {
        this.purchasePlanNo = purchasePlanNo;
    }

    public String getCreateEmployeeNo() {
        return createEmployeeNo;
    }

    public void setCreateEmployeeNo(String createEmployeeNo) {
        this.createEmployeeNo = createEmployeeNo;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPickingTicketId() {
        return pickingTicketId;
    }

    public void setPickingTicketId(Integer pickingTicketId) {
        this.pickingTicketId = pickingTicketId;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }
}
