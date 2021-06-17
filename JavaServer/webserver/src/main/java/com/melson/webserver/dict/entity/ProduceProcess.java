package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Messi on 2021/4/23
 */
@Entity
@Table(name = "produce_process")      //生产工序表
public class ProduceProcess {
    public static final String NO_START_WITH="PP";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String processNo;      // 生产工序编码，8位编码规则 例：PP000001
    private String name;           // 工序名称
    private Integer time;
    private String timeUnit;
    private BigDecimal cost;       // 工序费用，将来用于计算工序计件费用
    private String description;    // 描述备注
    private String createBy;       // 工序创建人
    private String delegateFlag;   //是否委外表示 Y 是 N 否

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessNo() {
        return processNo;
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDelegateFlag() {
        return delegateFlag;
    }

    public void setDelegateFlag(String delegateFlag) {
        this.delegateFlag = delegateFlag;
    }
}
