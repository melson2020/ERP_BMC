package com.melson.webserver.dict.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description 工艺字典
 * @Date 2021/5/10
 */
@Entity
@Table(name = "produce_tech")
public class ProduceTech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    private String employeeGroupName;
    private String employeeGroupNo;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeGroupName() {
        return employeeGroupName;
    }

    public void setEmployeeGroupName(String employeeGroupName) {
        this.employeeGroupName = employeeGroupName;
    }

    public String getEmployeeGroupNo() {
        return employeeGroupNo;
    }

    public void setEmployeeGroupNo(String employeeGroupNo) {
        this.employeeGroupNo = employeeGroupNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
