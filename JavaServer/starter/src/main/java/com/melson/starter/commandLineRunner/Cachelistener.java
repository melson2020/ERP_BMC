package com.melson.starter.commandLineRunner;


import com.melson.base.cache.CacheKey;
import com.melson.base.cache.CacheUtil;
import com.melson.base.entity.Area;
import com.melson.base.entity.City;
import com.melson.base.entity.Province;
import com.melson.base.entity.SystemConfig;
import com.melson.base.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * Created by Nelson on 2019/10/24.
 * 项目启动后执行的代码，可以完成一些自定义设置，目前为加载缓存
 * Spring Beans都初始化之后SpringApplication.run()之前执行
 */
@Component
@Order(value = 1)
public class Cachelistener implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Cachelistener.class);
    @Autowired
    private CacheUtil cacheUtil;
    private final IProvince provinceService;
    private final ICity cityService;
    private final IArea areaService;
    private final ISystemConfig configService;
    private final ISysDict sysDictService;

    public Cachelistener(IProvince provinceService, ICity cityService, IArea areaService, ISystemConfig configService,ISysDict sysDictService) {
        this.provinceService = provinceService;
        this.cityService = cityService;
        this.areaService = areaService;
        this.configService = configService;
        this.sysDictService = sysDictService;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("--------------------- init system cache -------------------------");
        //加载省份列表到缓存
        LoadProvince();
        //加载城市列表到缓存
        LoadCity();
        //加载区域列表到缓存
        LoadArea();
        //加载系统设置至缓存
        LoadSysConfig();
        //加载系统字典至缓存
        LoadSysDict();
        log.info("---------------------cache init completed ------------------------");

    }

    private void LoadSysConfig() {
        log.info("--------------------- add system configurations to cache -----------------------");
        Map<String, SystemConfig> configMap = configService.findAllMap();
        cacheUtil.Put(CacheKey.SystemConfig, configMap);
        log.info("--------------------- system configurations added ------------------------------");
    }


    private void LoadProvince() {
        log.info("--------------------- add province to cache -----------------------");
        List<Province> provinceList = provinceService.findAll();
        cacheUtil.Put(CacheKey.Province, provinceList);
        log.info("--------------------- province added ------------------------------");
    }

    private void LoadCity() {
        log.info("--------------------- add city to cache ---------------------------");
        List<City> cityList = cityService.findAll();
        cacheUtil.Put(CacheKey.City, cityList);
        log.info("--------------------- city added ----------------------------------");
    }

    private void LoadArea() {
        log.info("--------------------- add area to cache ---------------------------");
        List<Area> areaList = areaService.findAll();
        cacheUtil.Put(CacheKey.Area, areaList);
        log.info("--------------------- area added ----------------------------------");
    }

    private void LoadSysDict() {
        log.info("--------------------- add system dict to cache -----------------------");
        sysDictService.reloadCache();
        log.info("--------------------- system dict added ------------------------------");
    }
}
