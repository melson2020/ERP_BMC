package com.melson.base.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统字典类型实体
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
@Entity
@Table(name = "sys_dict_type")
public class SysDictType {

    /**
     * 唯一自增id
     */
    private Integer id;
    /**
     * 字典别名
     */
    private String alias;
    /**
     * 字典名称
     */
    private String name;
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

    @Column(name = "alias", nullable = false, unique = true, length = 100)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
