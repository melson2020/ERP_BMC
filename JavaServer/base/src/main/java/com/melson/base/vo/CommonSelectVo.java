package com.melson.base.vo;

/**
 * 通用下拉框对象
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
public class CommonSelectVo {

    /**
     * 键
     */
    private String code;
    /**
     * 值
     */
    private String value;
    /**
     * 扩展信息
     */
    private String extend;

    public CommonSelectVo() {

    }

    public CommonSelectVo(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public CommonSelectVo(String code, String value, String extend) {
        this.code = code;
        this.value = value;
        this.extend = extend;
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

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
