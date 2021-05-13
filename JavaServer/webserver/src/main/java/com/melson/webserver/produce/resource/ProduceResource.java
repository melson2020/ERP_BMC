package com.melson.webserver.produce.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.ProduceLine;
import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.order.service.IProducePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/11
 */
@RestController
@RequestMapping(value = "/produce")
public class ProduceResource extends BaseResource {
    @Autowired
    private IProducePlanService producePlanService;

    @GetMapping(value = "/producePlanList")
    public Result FindProducePlanList(String state){
        return success(producePlanService.FindPlanList(state));
    }
}
