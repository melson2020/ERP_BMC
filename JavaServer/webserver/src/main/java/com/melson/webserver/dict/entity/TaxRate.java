package com.melson.webserver.dict.entity;

import javax.persistence.*;

/**
 * Created by Messi on 2021/4/26
 */
@Entity
@Table(name = "tax_rate")      //税率表
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rate;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
