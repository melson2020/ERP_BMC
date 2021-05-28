package com.melson.webserver.dict.entity;

import com.melson.base.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/5/27
 */
@Entity
@Table(name="work_group")
public class WorkGroup {
    public static final String WORKGROUP_NO_CHAR="WG";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                   //
    private String workGroupCode;
    private String name;
    private String description;
    private String status;
    private String createBy;
    private Date createDate;

    @Transient
    private List<User> userList;
    @Transient
    private List<UserGroup> userGroups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkGroupCode() {
        return workGroupCode;
    }

    public void setWorkGroupCode(String workGroupCode) {
        this.workGroupCode = workGroupCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

}
