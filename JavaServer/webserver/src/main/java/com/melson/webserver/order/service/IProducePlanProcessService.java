package com.melson.webserver.order.service;

import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.order.entity.ProducePlanDetail;
import com.melson.webserver.order.entity.ProducePlanProcess;
import com.melson.webserver.produce.vo.ProducePlanProcessVo;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/12
 */
public interface IProducePlanProcessService {
    List<ProducePlanProcess> GeneratePlanProcess(ProducePlan plan,List<ProducePlanDetail>  detailList);
    List<ProducePlanProcess> FindByPlanId(Integer planId);
    List<ProducePlanProcessVo> FindPlanProcess(Integer planId, Integer planDetailId);

    void GeneratePlanProcessSeperately(ProducePlan savedPP, ProducePlanDetail savePPD);
}
