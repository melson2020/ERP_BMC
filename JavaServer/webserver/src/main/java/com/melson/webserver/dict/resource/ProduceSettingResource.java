package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.contract.resource.AbsContractResource;
import com.melson.webserver.dict.entity.ProduceLine;
import com.melson.webserver.dict.entity.ProduceTech;
import com.melson.webserver.dict.service.IProduceLineService;
import com.melson.webserver.dict.service.IProduceTechService;
import com.melson.webserver.dict.service.IProduceWorkStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
@RestController
@RequestMapping("/produceSetting")
public class ProduceSettingResource extends BaseResource {
    private static final Logger logger = LoggerFactory.getLogger(ProduceSettingResource.class);
    @Autowired
    private IProduceTechService produceTechService;
    @Autowired
    private IProduceLineService produceLineService;
    @Autowired
    private IProduceWorkStationService workStationService;

    @PostMapping("/saveProduceTech")
    public Result SaveProduceTech(@RequestBody ProduceTech tech) {
        ProduceTech saved = produceTechService.SaveOne(tech);
        if (saved == null) {
            return failure(-1, "保存失败");
        }
        return success(saved);
    }

    @GetMapping("/produceTechList")
    public Result ProduceTechList() {
        List<ProduceTech> list = produceTechService.findAll();
        if (list != null) {
            return success(list);
        } else {
            return failure(-1, "查询失败");
        }

    }

    @DeleteMapping("/deleteProduceTech")
    public Result DeleteProduceTech(HttpServletRequest request, Integer id) {
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(token);
        produceTechService.DeleteOne(id);
        logger.info("用户[{}]删除工艺[{}]成功", userId, id);
        return success(id);
    }

    @PostMapping("/saveProduceLine")
    public Result SaveProduceLine(@RequestBody ProduceLine produceLine) {
        if (produceLine == null) return null;
        ProduceLine saved = produceLineService.SaveOne(produceLine);
        if (saved == null) {
            return failure(-1, "保存失败");
        } else {
            return success(saved);
        }
    }

    @GetMapping("/produceLineList")
    public Result ProduceLineList() {
        return success(produceLineService.FindAll());
    }

    @GetMapping("/lineWorkStationList")
    public Result FindLineStationList(Integer lineId) {
        return success(workStationService.FindListByLineId(lineId));
    }

    @DeleteMapping("/deleteWorkStation")
    public Result DeleteWorkStation(HttpServletRequest request, Integer id){
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(token);
        workStationService.DeleteOne(id);
        logger.info("用户[{}]删除工位[{}]成功", userId, id);
        return success(id);
    }

    @DeleteMapping("/deleteProduceLine")
    public Result DeleteProduceLine(HttpServletRequest request, Integer id){
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(token);
        produceLineService.DeleteOne(id);
        logger.info("用户[{}]删除产线[{}]成功", userId, id);
        return success(id);
    }
}
