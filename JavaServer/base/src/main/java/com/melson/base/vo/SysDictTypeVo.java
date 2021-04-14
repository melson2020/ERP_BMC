package com.melson.base.vo;

import java.util.List;

/**
 * 系统字典类型vo
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
public class SysDictTypeVo {

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
     * 字典明细集合
     */
    List<SysDictVo> dictValueList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<SysDictVo> getDictValueList() {
        return dictValueList;
    }

    public void setDictValueList(List<SysDictVo> dictValueList) {
        this.dictValueList = dictValueList;
    }
}
