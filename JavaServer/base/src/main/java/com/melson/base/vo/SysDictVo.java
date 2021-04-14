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
    private Integer order;
    /**
     * 键值对描述
     */
    private String desc;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
