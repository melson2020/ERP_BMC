package com.melson.base.service.impl;

import com.melson.base.service.ISysCache;
import com.melson.base.service.ISysDict;
import com.melson.base.vo.SysDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统缓存实现类
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
@Service
public class SysCacheImpl implements ISysCache {

    @Autowired
    private ISysDict sysDictService;

    @Override
    public List<SysDictVo> getDictList(Integer typeId) {
        return sysDictService.list(typeId);
    }

    @Override
    public SysDictVo getDict(Integer typeId, String code) {
        return sysDictService.get(typeId, code);
    }
}
