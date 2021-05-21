package com.melson.webserver.produce.vo;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/20
 */
public class ProduceWorkStationStateVo {
    private Integer lineId;
    private String indexNo;
    private String workStationName;
    private String employeeGroupNo;
    private Integer workStationId;

    private List<ProcessingWorkStationVo> planInfo;

    public List<ProcessingWorkStationVo> getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(List<ProcessingWorkStationVo> planInfo) {
        this.planInfo = planInfo;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getEmployeeGroupNo() {
        return employeeGroupNo;
    }

    public void setEmployeeGroupNo(String employeeGroupNo) {
        this.employeeGroupNo = employeeGroupNo;
    }

    public Integer getWorkStationId() {
        return workStationId;
    }

    public void setWorkStationId(Integer workStationId) {
        this.workStationId = workStationId;
    }
}
