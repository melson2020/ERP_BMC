package com.melson.webserver.dict.vo;


public class UserPassVo {
    private Integer id;
    private String alias;

    public UserPassVo() {
    }

    public UserPassVo(Integer id, String alias) {
        this.id = id;
        this.alias = alias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
