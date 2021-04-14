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
     * 字典描述
     */
    private String desc;
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
    @Column(name = "n_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "s_alias", nullable = false, unique = true, length = 100)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Column(name = "s_name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "s_desc", length = 50)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "d_modify", nullable = false)
    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }

    @Column(name = "n_modify_user", nullable = false)
    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Column(name = "s_state", nullable = false, length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
