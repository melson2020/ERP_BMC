package com.melson.base.vo;

/**
 * 系统字典vo
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
public class SysDictVo {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
