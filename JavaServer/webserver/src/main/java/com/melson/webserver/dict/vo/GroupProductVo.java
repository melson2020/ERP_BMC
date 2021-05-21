package com.melson.webserver.dict.vo;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public class GroupProductVo {
    private String groupName;
    private List<ProductVo> list;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ProductVo> getList() {
        return list;
    }

    public void setList(List<ProductVo> list) {
        this.list = list;
    }
}
