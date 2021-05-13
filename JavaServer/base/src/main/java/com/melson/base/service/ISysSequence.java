package com.melson.base.service;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/11
 */
public interface ISysSequence {
    /**
     * 生成有序编码 如需生成PP0001 key=PP
     * @param key 编码key值
     * @return
     */
    String GenerateCode(String key);
}
