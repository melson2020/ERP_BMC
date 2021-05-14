package com.melson.webserver.produce.vo;

import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.produce.entity.ProducePlanWorkStation;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/13
 */
public class ProducePlanConfirmInfoVo {
    private ProducePlan plan;
    private List<ProducePlanWorkStation> workStationList;
    private Boolean confirm;

    public ProducePlan getPlan() {
        return plan;
    }

    public void setPlan(ProducePlan plan) {
        this.plan = plan;
    }

    public List<ProducePlanWorkStation> getWorkStationList() {
        return workStationList;
    }

    public void setWorkStationList(List<ProducePlanWorkStation> workStationList) {
        this.workStationList = workStationList;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }
}
