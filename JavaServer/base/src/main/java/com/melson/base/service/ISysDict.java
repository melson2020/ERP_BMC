package com.melson.base.service;

import com.melson.base.entity.SysDict;
import com.melson.base.vo.SysDictVo;

import java.util.List;

/**
 * 系统字典类型接口
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
public interface ISysDict {

    /**
     * 刷新缓存
     *
     * @return
     */
    void reloadCache();

    /**
     * 根据字典类型id查询所有字典明细集合
     *
     * @param typeId
     * @return
     */
    List<SysDictVo> list(Integer typeId);

    /**
     * 根据字典类型id+字典编码查询字典明细
     *
     * @param typeId
     * @param code
     * @return
     */
    SysDictVo get(Integer typeId, String code);
}
