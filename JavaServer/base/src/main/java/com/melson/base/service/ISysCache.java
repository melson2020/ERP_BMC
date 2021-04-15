package com.melson.base.service;

import com.melson.base.vo.SysDictVo;

import java.util.List;

/**
 * 系统缓存接口
 * 统一缓存获取入口
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
public interface ISysCache {

    /**
     * 根据字典类型id查询所有字典明细集合
     *
     * @param typeId
     * @return
     */
    List<SysDictVo> getDictList(Integer typeId);

    /**
     * 根据字典类型id+字典编码查询字典明细
     *
     * @param typeId
     * @param code
     * @return
     */
    SysDictVo getDict(Integer typeId, String code);
}
