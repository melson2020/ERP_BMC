package com.melson.webserver.contract.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 合同额外属性枚举
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
public enum ContractExtendEnum {

    /**
     * 合同描述
     */
    DESC("desc", "合同描述"),
    /**
     * 合同备注
     */
    REMARKS("remarks", "合同备注"),
    /**
     * 合同说明
     */
    EXPLAIN("explain", "合同说明");

    /**
     * 属性字段名
     */
    private final String alias;
    /**
     * 属性字段
     */
    private final String name;

    ContractExtendEnum(String alias, String name) {
        this.alias = alias;
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据属性字段名获取属性枚举
     *
     * @param alias 属性字段名
     * @return
     */
    public ContractExtendEnum getEnum(String alias) {
        for (ContractExtendEnum ext : values()) {
            if (!StringUtils.isEmpty(alias) && alias.equals(ext.getAlias())) {
                return ext;
            }
        }
        return null;
    }
}
