package com.melson.base.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.cache.CacheKey;
import com.melson.base.cache.CacheUtil;
import com.melson.base.dao.ISystemConfigDao;
import com.melson.base.entity.SystemConfig;
import com.melson.base.service.ISystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/22
 */
@Service
public class SystemConfigImpl extends AbstractService<SystemConfig> implements ISystemConfig {
    private final ISystemConfigDao systemConfigDao;
    private final CacheUtil cacheUtil;

    public SystemConfigImpl(ISystemConfigDao systemConfigDao, CacheUtil cacheUtil) {
        this.systemConfigDao = systemConfigDao;
        this.cacheUtil = cacheUtil;
    }

    @Override
    public JpaRepository<SystemConfig, String> getRepository() {
        return systemConfigDao;
    }

    @Override
    public Map<String, SystemConfig> findAllMap() {
        List<SystemConfig> list=systemConfigDao.findAll();
        Map<String,SystemConfig> map=new HashMap<>(list.size());
        for (SystemConfig config:list){
            map.put(config.getKey(),config);
        }
        return map;
    }

    @Override
    public List<SystemConfig> findAll() {
        return systemConfigDao.findAll();
    }

    @Override
    public SystemConfig findFormCache(String key) {
        Map<String,SystemConfig> sysConfigMap=cacheUtil.GetObjectValue(CacheKey.SystemConfig,Map.class);
        SystemConfig systemConfig=sysConfigMap.get(key);
        if(systemConfig==null){
            Map<String,SystemConfig> configMap=findAllMap();
            systemConfig=configMap.get(key);
            cacheUtil.Put(CacheKey.SystemConfig,configMap);
        }
        return systemConfig;
    }
}
