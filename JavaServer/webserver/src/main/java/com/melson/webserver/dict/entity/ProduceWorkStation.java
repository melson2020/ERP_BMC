package com.melson.webserver.dict.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description 产线工位列表
 * @Date 2021/5/10
 */
@Entity
@Table(name = "produce_work_station")
public class ProduceWorkStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String indexNo;
    private String name;
    private Integer produceProcessId;
    private String produceProcessName;
    private String employeeGroupNo;
    private Integer produceLineId;
    private String produceLineName;
    private String employeeGroupName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProduceProcessId() {
        return produceProcessId;
    }

    public void setProduceProcessId(Integer produceProcessId) {
        this.produceProcessId = produceProcessId;
    }

    public String getProduceProcessName() {
        return produceProcessName;
    }

    public void setProduceProcessName(String produceProcessName) {
        this.produceProcessName = produceProcessName;
    }

    public String getEmployeeGroupNo() {
        return employeeGroupNo;
    }

    public void setEmployeeGroupNo(String employeeGroupNo) {
        this.employeeGroupNo = employeeGroupNo;
    }

    public String getEmployeeGroupName() {
        return employeeGroupName;
    }

    public void setEmployeeGroupName(String employeeGroupName) {
        this.employeeGroupName = employeeGroupName;
    }

    public Integer getProduceLineId() {
        return produceLineId;
    }

    public void setProduceLineId(Integer produceLineId) {
        this.produceLineId = produceLineId;
    }

    public String getProduceLineName() {
        return produceLineName;
    }

    public void setProduceLineName(String produceLineName) {
        this.produceLineName = produceLineName;
    }
}
