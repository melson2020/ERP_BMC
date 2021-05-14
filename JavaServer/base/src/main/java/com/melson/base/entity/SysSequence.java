package com.melson.base.entity;

import javax.persistence.*;

/**
 * @Author Nelson
 * @Description 系统自增长序列 用于生成有序编码
 * @Date 2021/5/11
 */
@Entity
@Table(name = "sys_sequence")
public class SysSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private Integer value;
    private Integer valueLength;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public Integer getValueLength() {
        return valueLength;
    }

    public void setValueLength(Integer valueLength) {
        this.valueLength = valueLength;
    }
}
