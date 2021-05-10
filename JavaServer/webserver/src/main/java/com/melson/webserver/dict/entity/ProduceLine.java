package com.melson.webserver.dict.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @Author Nelson
 * @Description 产线字典表
 * @Date 2021/5/10
 */
@Entity
@Table(name="produce_line")
public class ProduceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;
    private String code;
    private String name;
    private String location;
    private String description;

    @Transient
    private List<ProduceWorkStation> workStationList;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProduceWorkStation> getWorkStationList() {
        return workStationList;
    }

    public void setWorkStationList(List<ProduceWorkStation> workStationList) {
        this.workStationList = workStationList;
    }
}
