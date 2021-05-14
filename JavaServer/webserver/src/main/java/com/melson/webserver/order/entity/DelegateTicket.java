package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author Nelson
 * @Description 委外计划单
 * @Date 2021/5/6
 */
@Entity
@Table(name = "delegate_ticket")
public class DelegateTicket {
    public static  final  String TYPE_ORDER="ORDER";
    public static  final String TYPE_PLAN="PLAN";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //生成 委外的 id 如 订单则为 orderFormId
    private Integer sourceId;
    //生成 委外的 no 如 订单则为 orderFormNo
    private String sourceNo;
    private String customerNo;
    private String customerName;
    private String contractNo;
    private String ticketNo;
    private Date createDate;
    /**
     * 委外单类型 ORDER 订单委外 PLAN 生产计划工序委外
     */
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
