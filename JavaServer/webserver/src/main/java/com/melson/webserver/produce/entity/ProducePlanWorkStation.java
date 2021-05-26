package com.melson.webserver.produce.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description 生产计划 工位配置
 * @Date 2021/5/13
 */
@Entity
@Table(name = "produce_plan_work_station")
public class ProducePlanWorkStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer planId;

    private Integer workStationId;
    private Integer processId;
    private String workStationName;
    private Integer produceLineId;
    private String produceLineName;
    private String employeeGroupName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkStationId() {
        return workStationId;
    }

    public void setWorkStationId(Integer workStationId) {
        this.workStationId = workStationId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getProduceLineName() {
        return produceLineName;
    }

    public void setProduceLineName(String produceLineName) {
        this.produceLineName = produceLineName;
    }

    public String getEmployeeGroupName() {
        return employeeGroupName;
    }

    public void setEmployeeGroupName(String employeeGroupName) {
        this.employeeGroupName = employeeGroupName;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getProduceLineId() {
        return produceLineId;
    }

    public void setProduceLineId(Integer produceLineId) {
        this.produceLineId = produceLineId;
    }


}
