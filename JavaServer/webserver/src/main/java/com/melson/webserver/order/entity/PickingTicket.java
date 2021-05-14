package com.melson.webserver.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author Nelson
 * @Description 领料单
 * @Date 2021/5/7
 */
@Entity
@Table(name = "picking_ticket")
public class PickingTicket {
    public static final String TICKET_NO_CHAR="L";
    public static final String TYPE_ORDER="ORDER";
    public static final String TYPE_PLAN="PLAN";

    public static final String STATE_CREATE="CREATE";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ticketNo;
    //源头ID 如 生产计划 则为生产计划ID
    private Integer sourceId;
    private String sourceNo;
    //类型 如 订单生成则为 ORDER 生产计划生成 则为PLAN
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

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }
}
