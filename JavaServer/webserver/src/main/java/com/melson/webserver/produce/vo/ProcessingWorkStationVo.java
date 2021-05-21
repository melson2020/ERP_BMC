package com.melson.webserver.produce.vo;

/**
 * @Author Nelson
 * @Description 在在使用的工位信息
 * @Date 2021/5/20
 */
public class ProcessingWorkStationVo {
    private Integer planId;
    private Integer workStationId;
    private String planState;
    private String planNo;

    public ProcessingWorkStationVo() {

    }

    public ProcessingWorkStationVo(Integer planId, Integer workStationId, String planState, String planNo) {
        this.planId = planId;
        this.workStationId = workStationId;
        this.planState = planState;
        this.planNo = planNo;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getWorkStationId() {
        return workStationId;
    }

    public void setWorkStationId(Integer workStationId) {
        this.workStationId = workStationId;
    }

    public String getPlanState() {
        return planState;
    }

    public void setPlanState(String planState) {
        this.planState = planState;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }
}
