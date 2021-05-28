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
     * 根据id前面拼接0,默认10位,拼接前缀
     *
     * @param prefix 编码前缀
     * @param id
     * @return
     */
    public static String incrementCode(String prefix, Integer id) {
        return incrementCode(id, prefix, DEFAULT_LENGTH);
    }

    public static String incrementCode(Integer id, String codeChar, Integer length) {
        return String.format(codeChar + "%0" + length + "d", id);
    }
}
