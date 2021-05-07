package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author Nelson
 * @Description 生产取料单
 * @Date 2021/5/7
 */
@Entity
@Table(name = "produce_picking_ticket")
public class ProducePickingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ticketNo;
    private Integer producePlanId;
    private String producePlanNo;
    //类型 如：代工需要委托方供料 P 为自己生产 D 为代工 W 委外
    private String type;
    private Date createDate;
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Integer getProducePlanId() {
        return producePlanId;
    }

    public void setProducePlanId(Integer producePlanId) {
        this.producePlanId = producePlanId;
    }

    public String getProducePlanNo() {
        return producePlanNo;
    }

    public void setProducePlanNo(String producePlanNo) {
        this.producePlanNo = producePlanNo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
