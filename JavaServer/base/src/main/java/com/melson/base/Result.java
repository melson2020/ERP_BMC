package com.melson.base;

import com.melson.base.constants.SysRespCode;

/**
 * Created by Nelson on 2020/6/17.
 */
public class Result {

    /**
     * 返回码
     */
    private Integer resultStatus;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public Result() {
        this.resultStatus = SysRespCode.SUCCESS;
    }

    public Result(Object data) {
        this.resultStatus = SysRespCode.SUCCESS;
        this.data = data;

    }

    public Result(Integer resultStatus, String message) {
        this.resultStatus = resultStatus;
        this.message = message;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
