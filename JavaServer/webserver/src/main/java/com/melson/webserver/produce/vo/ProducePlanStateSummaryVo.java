package com.melson.webserver.produce.vo;

/**
 * @Author Nelson
 * @Description 生产计划各状态数量信息
 * @Date 2021/5/20
 */
public class ProducePlanStateSummaryVo {
    private Integer confirmCount;
    private Integer processCount;

    public ProducePlanStateSummaryVo(Integer confirmCount, Integer processCount) {
        this.confirmCount = confirmCount;
        this.processCount = processCount;
    }

    public Integer getConfirmCount() {
        return confirmCount;
    }

    public void setConfirmCount(Integer confirmCount) {
        this.confirmCount = confirmCount;
    }

    public Integer getProcessCount() {
        return processCount;
    }

    public void setProcessCount(Integer processCount) {
        this.processCount = processCount;
    }
}
