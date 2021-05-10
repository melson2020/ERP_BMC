package com.melson.webserver.dict.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.contract.resource.AbsContractResource;
import com.melson.webserver.dict.entity.ProduceLine;
import com.melson.webserver.dict.entity.ProduceTech;
import com.melson.webserver.dict.service.IProduceTechService;
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

    @PostMapping("/saveProduceTech")
    public Result SaveProduceTech(@RequestBody ProduceTech tech) {
        ProduceTech saved=produceTechService.SaveOne(tech);
        if(saved==null){
            return failure(-1,"保存失败");
        }
        return success(saved);
    }

    @GetMapping("/produceTechList")
    public Result ProduceTechList() {
        List<ProduceTech> list=produceTechService.findAll();
        if(list!=null){
            return success(list);
        }else {
            return failure(-1,"查询失败");
        }

    }

    @DeleteMapping("/deleteProduceTech")
    public Result DeleteProduceTech(HttpServletRequest request, Integer id) {
        String token=request.getHeader("token");
        Integer userId=Integer.parseInt(token);
        produceTechService.DeleteOne(id);
        logger.info("用户[{}]删除工艺[{}]成功", userId, id);
        return success(id);
    }

    @PostMapping("/saveProduceLine")
    public Result SaveProdcueLine(@RequestBody ProduceLine produceLine){
      return null;
    }
}
