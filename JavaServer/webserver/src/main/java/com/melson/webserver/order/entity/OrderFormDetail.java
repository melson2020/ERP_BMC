package com.melson.webserver.order.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/4/30
 */
@Entity
@Table(name = "order_form_detail")
public class OrderFormDetail {

    //生产类型
    public  static final String PRODUCE_TYPE_P="P";

    //采购
    public  static final String PRODUCE_TYPE_C="C";

    //代工
    public  static final String PRODUCE_TYPE_D="D";

    //委外
    public  static final String PRODUCE_TYPE_W="W";
    /**
     * 订单唯一自增id,用于其他表关联
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 订单Id
     */
    private Integer orderFormId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品自增长Id
     */
    private Integer productId;
    /**
     * 订单产品数量
     */
    private Integer count;
    /**
     * 订单产品数量单位
     */
    private String countUnit;
    /**
     * 规格
     */
    private String specification;
    /**
     * 备注
     */
    private String remark;
    /**
     * 生产类型 生产/采购/代加工/委外
     */
    private String produceType;

    /**
     * BOM 选择  优先半成品/ 优先底层物料
     */
    private String bomChoice;

    /**
     * BOM NO  选择的BOM 编号
     */
    private String bomNo;

    /**
     * 产品编号
     */
    private String productNo;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
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

    public String getProduceType() {
        return produceType;
    }

    public void setProduceType(String produceType) {
        this.produceType = produceType;
    }

    public String getBomChoice() {
        return bomChoice;
    }

    public void setBomChoice(String bomChoice) {
        this.bomChoice = bomChoice;
    }

    public String getBomNo() {
        return bomNo;
    }

    public void setBomNo(String bomNo) {
        this.bomNo = bomNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
}
