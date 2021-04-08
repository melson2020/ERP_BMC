package com.melson.base.service;

import com.melson.base.IService;
import com.melson.base.entity.SystemConfig;

import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/22
 */
public interface ISystemConfig extends IService<SystemConfig> {
    Map<String,SystemConfig> findAllMap();
    List<SystemConfig> findAll();
    SystemConfig findFormCache(String key);
}
