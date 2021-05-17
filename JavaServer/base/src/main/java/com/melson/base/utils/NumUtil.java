package com.melson.base.utils;

/**
 * 数字编码工具类
 *
 * @author wuhuan
 * @date 2021年05月09日
 */
public class NumUtil {

    /**
     * 默认编码长度
     */
    public static final int DEFAULT_LENGTH = 10;

    /**
     * 根据id前面拼接0,默认10位
     *
     * @param id
     * @return
     */
    public static String incrementNum(int id) {
        return incrementNum(id, DEFAULT_LENGTH);
    }

    /**
     * 根据id前面拼接0
     *
     * @param id
     * @param length
     * @return
     */
    public static String incrementNum(int id, int length) {
        return String.format("%0" + length + "d", id);
    }
}
