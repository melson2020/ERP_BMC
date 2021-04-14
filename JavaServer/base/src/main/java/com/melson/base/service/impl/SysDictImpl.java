package com.melson.base.service.impl;

import com.melson.base.cache.CacheKey;
import com.melson.base.cache.CacheUtil;
import com.melson.base.constants.SysConstants;
import com.melson.base.dao.ISysDictDao;
import com.melson.base.dao.ISysDictTypeDao;
import com.melson.base.entity.SysDict;
import com.melson.base.entity.SysDictType;
import com.melson.base.service.ISysDict;
import com.melson.base.vo.SysDictTypeVo;
import com.melson.base.vo.SysDictVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统字典接口实现类
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
@Service
public class SysDictImpl implements ISysDict {

    private static final Logger logger = LoggerFactory.getLogger(SysDictImpl.class);

    @Autowired
    private ISysDictTypeDao sysDictTypeDao;
    @Autowired
    private ISysDictDao sysDictDao;
    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public void reloadCache() {
        List<SysDictType> typeList = sysDictTypeDao.findByState(SysConstants.COMMON_ENABLE);
        if (typeList == null || typeList.isEmpty()) {
            logger.error("系统字典类型为空");
            return;
        }
        Map<Integer, SysDictTypeVo> cache = new HashMap<>(typeList.size());
        typeList.forEach(dictType -> {
            SysDictTypeVo vo = new SysDictTypeVo();
            BeanUtils.copyProperties(dictType, vo);
            cache.put(dictType.getId(), vo);
        });
        List<SysDict> list = sysDictDao.findByState(SysConstants.COMMON_ENABLE);
        if (list == null || list.isEmpty()) {
            logger.error("系统字典为空");
        } else {
            list.sort(Comparator.comparingInt(SysDict::getTypeId).thenComparingInt(SysDict::getOrder));
            list.forEach(dictDetail -> {
                if (cache.containsKey(dictDetail.getTypeId())) {
                    SysDictTypeVo dictTypeVo = cache.get(dictDetail.getTypeId());
                    if (dictTypeVo.getDictValueList() == null) {
                        dictTypeVo.setDictValueList(new ArrayList<>());
                    }
                    SysDictVo detailVo = new SysDictVo();
                    BeanUtils.copyProperties(dictDetail, detailVo);
                    dictTypeVo.getDictValueList().add(detailVo);
                }
            });
        }
        cacheUtil.Put(CacheKey.SYS_DICT_CACHE_KEY, cache);
        logger.info("成功缓存[{}]条数据字典", cache.size());
    }

    @Override
    public List<SysDictVo> list(Integer typeId) {
        if (typeId == null) {
            logger.error("系统字典id为空");
            return null;
        }
        @SuppressWarnings("unchecked")
        Map<Integer, SysDictTypeVo> dictTypeMap = cacheUtil.GetObjectValue(CacheKey.SYS_DICT_CACHE_KEY, Map.class);
        if (!dictTypeMap.containsKey(typeId)) {
            logger.error("系统字典类型[{}]无效", typeId);
            return null;
        }
        return dictTypeMap.get(typeId).getDictValueList();
    }

    @Override
    public SysDictVo get(Integer typeId, String code) {
        List<SysDictVo> list = list(typeId);
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (StringUtils.isEmpty(code)) {
            logger.error("系统字典编码为空");
            return null;
        }
        for (SysDictVo detailVo : list) {
            if (code.equals(detailVo.getCode())) {
                return detailVo;
            }
        }
        return null;
    }
}