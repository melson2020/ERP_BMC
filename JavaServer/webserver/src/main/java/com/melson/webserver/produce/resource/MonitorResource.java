package com.melson.webserver.produce.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.service.IProduceLineService;
import com.melson.webserver.order.service.IProducePlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/20
 */
@RestController
@RequestMapping(value = "/produceMonitor")
public class MonitorResource extends BaseResource {
    private static final Logger logger = LoggerFactory.getLogger(MonitorResource.class);
    @Autowired
    private IProducePlanService producePlanService;
    @Autowired
    private IProduceLineService produceLineService;

    @GetMapping(value = "/countSummary")
    public Result GetPlanStateCountSummary() {
        return success(producePlanService.GetStateSummary());
    }

    @GetMapping(value = "/processingPlanList")
    public Result GetProcessingPlanList() {
        return success(producePlanService.GetProcessingPlanAndPickingTicketInfo());
    }

    @GetMapping(value = "/produceLineStateInfo")
    public Result GetProduceLineStateInfo() {
        return success(produceLineService.GetProduceLineStateInfo());
    }

    @GetMapping(value = "/producePlanInfo")
    public Result GetProducePlanInfo(Integer planId) {
        return success(producePlanService.FindPlanInfo(planId));
    }
}
