package com.melson.base.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统字典实体
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
@Entity
@Table(name = "sys_dict")
public class SysDict {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 数据字典类型id
     */
    private Integer typeId;
    /**
     * 键
     */
    private String code;
    /**
     * 值
     */
    private String value;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述备注
     */
    private String comments;
    /**
     * 最新编辑日期
     */
    private Date modify;
    /**
     * 最新编辑人,关联员工表
     */
    private Integer modifyUser;
    /**
     * 状态:0=禁用;1=启用;
     */
    private String state;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "typeId", nullable = false)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Column(name = "code", nullable = false, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "value", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "sort", nullable = false)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "comments", length = 50)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify", nullable = false)
    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }

    @Column(name = "modifyUser", nullable = false)
    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Column(name = "state", nullable = false, length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
