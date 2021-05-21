package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.ProduceLine;
import com.melson.webserver.dict.entity.ProduceProcess;
import com.melson.webserver.dict.service.IProduceLineService;
import com.melson.webserver.dict.service.IProduceProcess;
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
    private IProduceLineService produceLineService;
    @Autowired
    private IProduceWorkStationService workStationService;
    @Autowired
    private IProduceProcess produceProcessService;

    @PostMapping("/saveProduceProcess")
    public Result SaveProduceProcess(HttpServletRequest request,@RequestBody ProduceProcess produceProcess) {
        String token = request.getHeader("token");
        ProduceProcess saved = produceProcessService.SaveOne(produceProcess,token);
        if (saved == null) {
            return failure(-1, "保存失败");
        }
        return success(saved);
    }

    @GetMapping("/produceProcessList")
    public Result ProduceProcessList() {
        List<ProduceProcess> list = produceProcessService.findAll();
        if (list != null) {
            return success(list);
        } else {
            return failure(-1, "查询失败");
        }

    }

    @DeleteMapping("/deleteProduceProcess")
    public Result DeleteProduceProcess(HttpServletRequest request, Integer id) {
        String token = request.getHeader("token");
        Integer userId = Integer.parseInt(token);
        produceProcessService.DeleteOne(id);
        logger.info("用户[{}]删除工序[{}]成功", userId, id);
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
