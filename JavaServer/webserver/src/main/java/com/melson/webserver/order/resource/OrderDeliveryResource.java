package com.melson.webserver.order.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.order.entity.OrderDelivery;
import com.melson.webserver.order.service.IOrderDeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/16
 */
@RestController
@RequestMapping(value = "/orderDelivery")
public class OrderDeliveryResource extends BaseResource {
    private static final Logger logger = LoggerFactory.getLogger(OrderDeliveryResource.class);

    @Autowired
    private IOrderDeliveryService orderDeliveryService;

    @PostMapping(value = "/confirm")
    public Result OrderDeliveryConfirm(@RequestBody OrderDelivery orderDelivery){
          OrderDelivery saved=orderDeliveryService.Confirm(orderDelivery);
          if(saved==null){
              return failure(-1,"保存失败");
          }else {
              return success(saved);
          }
    }

    @GetMapping(value = "/list")
    public Result OrderDeliveryList(String state){
      return success(orderDeliveryService.FindByState(state));
    }
}
