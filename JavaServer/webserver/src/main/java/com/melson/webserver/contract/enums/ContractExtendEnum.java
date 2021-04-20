package com.melson.webserver.contract.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    DESC("desc", "合同描述", true),
    /**
     * 合同备注
     */
    REMARKS("remarks", "合同备注", true),
    /**
     * 合同说明
     */
    EXPLAIN("explain", "合同说明", true);

    /**
     * 属性字段名
     */
    private final String alias;
    /**
     * 属性字段
     */
    private final String name;
    /**
     * 属性字段是否必填
     */
    private final boolean required;

    ContractExtendEnum(String alias, String name, boolean required) {
        this.alias = alias;
        this.name = name;
        this.required = required;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public boolean getRequired() {
        return required;
    }

    /**
     * 获取所有合同额外属性
     *
     * @return
     */
    public static List<ContractExtendEnum> list() {
        List<ContractExtendEnum> list = new ArrayList<>();
        Collections.addAll(list, values());
        return list;
    }
}
