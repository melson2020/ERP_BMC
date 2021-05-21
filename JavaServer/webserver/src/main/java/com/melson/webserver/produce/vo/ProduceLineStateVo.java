package com.melson.webserver.produce.vo;

import java.util.List;

/**
 * @Author Nelson
 * @Description 长线工位状态载体
 * @Date 2021/5/20
 */
public class ProduceLineStateVo {
    private Integer lineId;
    private String lineName;

    private List<ProduceWorkStationStateVo> workStationVoList;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public List<ProduceWorkStationStateVo> getWorkStationVoList() {
        return workStationVoList;
    }

    public void setWorkStationVoList(List<ProduceWorkStationStateVo> workStationVoList) {
        this.workStationVoList = workStationVoList;
    }
}
