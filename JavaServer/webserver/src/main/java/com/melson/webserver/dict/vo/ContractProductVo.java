package com.melson.webserver.dict.vo;

import java.util.ArrayList;

/**
 * Created by Messi on 2021/4/26
 */
public class ContractProductVo {
    private String groupName;
    private ArrayList<ProductVo> list;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<ProductVo> getList() {
        return list;
    }

    public void setList(ArrayList<ProductVo> list) {
        this.list = list;
    }
}
