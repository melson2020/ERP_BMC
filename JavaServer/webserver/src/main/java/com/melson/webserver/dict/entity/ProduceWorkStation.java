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
    private String index;
    private String name;
    private Integer techId;
    private String techName;
    private String employeeGroupNo;
    private String employeeGroupName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
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
}
