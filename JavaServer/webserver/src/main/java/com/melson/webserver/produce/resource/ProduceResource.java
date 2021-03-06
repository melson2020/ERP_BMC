package com.melson.webserver.produce.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.webserver.dict.entity.ProduceLine;
import com.melson.webserver.dict.resource.ProduceSettingResource;
import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.service.IProduceWorkStationService;
import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.order.entity.ProducePlanDetail;
import com.melson.webserver.order.entity.ProducePlanProcess;
import com.melson.webserver.order.service.IProducePlanProcessService;
import com.melson.webserver.order.service.IProducePlanService;
import com.melson.webserver.produce.entity.ProducePlanWorkStation;
import com.melson.webserver.produce.service.IProducePlanWorkStationService;
import com.melson.webserver.produce.vo.ProducePlanConfirmInfoVo;
import com.melson.webserver.produce.vo.ProducePlanProcessVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/11
 */
@RestController
@RequestMapping(value = "/produce")
public class ProduceResource extends BaseResource {
    private static final Logger logger = LoggerFactory.getLogger(ProduceResource.class);
    @Autowired
    private IProducePlanService producePlanService;
    @Autowired
    private IProducePlanProcessService producePlanProcessService;
    @Autowired
    private IProduceWorkStationService produceWorkStationService;
    @Autowired
    private IProducePlanWorkStationService planWorkStationService;

    @GetMapping(value = "/producePlanList")
    public Result FindProducePlanList(String state){
        return success(producePlanService.FindPlanList(state));
    }

    @GetMapping(value = "/planDetailList")
    public Result FindProducePlanDetail(Integer planId){
        List<ProducePlanDetail> list=producePlanService.FindPlanDetailList(planId);
        if(list!=null){
            return success(list);
        }else {
            return failure(-1,"????????????");

        }
    }

    @GetMapping(value = "/planRecord")
    public Result FindProducePlanRecord(HttpServletRequest request){
        Map<String,String[]> kvMap=request.getParameterMap();
        List<ProducePlan> list=producePlanService.FindProducePlanRecord(kvMap);
        if(list!=null){
            return success(list);
        }else {
            return failure(-1,"????????????");

        }
    }

    @GetMapping(value = "/planDetailProcessList")
    public Result FindProducePlanDetailProcessList(Integer planId,Integer planDetailId){
        List<ProducePlanProcessVo> list=producePlanProcessService.FindPlanProcess(planId,planDetailId);
        if(list!=null){
            return success(list);
        }else {
            return failure(-1,"????????????");

        }
    }

    @GetMapping(value = "/workStationList")
    public Result FindALLWorkStationList(Integer processId){
        return success(produceWorkStationService.FindListByProcessId(processId));
    }

    @PostMapping(value = "/saveProducePlanInfo")
    @Transactional
    public Result SaveProducePlanInfo(@RequestBody ProducePlanConfirmInfoVo vo){
        ProducePlan plan=vo.getPlan();
        List<ProducePlanWorkStation> workStationList=vo.getWorkStationList();
        if(plan==null)return this.GenerateResult(ResultType.ParameterNeeded);
        planWorkStationService.SaveList(workStationList);
        ProducePlan savePlan=producePlanService.UpdatePlan(plan,vo.getConfirm());
        return success(savePlan);
    }

    @DeleteMapping(value = "/deletePlanWorkStation")
    public Result DeletePlanWorkStation(HttpServletRequest request,Integer id){
        String token = request.getHeader("token");
        planWorkStationService.DeleteOnById(id);
        logger.info("??????[{}]??????????????????????????????[{}]??????", token, id);
        return success(id);
    }



}
