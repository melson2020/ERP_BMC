package com.melson.webserver.produce.vo;

import com.melson.webserver.produce.entity.ProducePlanWorkStation;

import java.util.List;

/**
 * @Author Nelson
 * @Description 装载生产计划 流程 信息 会合并相同的工序
 * @Date 2021/5/12
 */
public class ProducePlanProcessVo {
    private Integer planId;
    private Integer processId;
    private String processNo;
    private String processName;
    private List<ProducePlanMaterialVo> materialVos;
    private List<ProducePlanWorkStation> workStationList;
    private String bomNo;
    private String delegateFlag;
    private String index;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getProcessNo() {
        return processNo;
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public List<ProducePlanMaterialVo> getMaterialVos() {
        return materialVos;
    }

    public void setMaterialVos(List<ProducePlanMaterialVo> materialVos) {
        this.materialVos = materialVos;
    }

    public String getBomNo() {
        return bomNo;
    }

    public void setBomNo(String bomNo) {
        this.bomNo = bomNo;
    }

    public String getDelegateFlag() {
        return delegateFlag;
    }

    public void setDelegateFlag(String delegateFlag) {
        this.delegateFlag = delegateFlag;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<ProducePlanWorkStation> getWorkStationList() {
        return workStationList;
    }

    public void setWorkStationList(List<ProducePlanWorkStation> workStationList) {
        this.workStationList = workStationList;
    }
}
