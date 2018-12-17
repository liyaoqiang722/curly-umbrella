package com.wj.spc.demo_1203.commons.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyaoqiang on 2018/11/22.
 */
public enum EmailType implements IEnum<EmailType, String> {
    /**
     *
     */
    CUSTOMER_JOIN("1", "保存客户信息"),
    /**
     *
     */
    LAWYER_JOIN("2", "入驻无讼"),
    /**
     *
     */
    VISIT_JOIN("3", "预约参观");

    /**
     * enum map
     */
    private static Map<String, EmailType> enumMap = new HashMap();

    static {
        for (EmailType type : EmailType.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    /**
     * key
     */
    private String value;
    /**
     * value
     */
    private String label;

    EmailType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 获取值
     *
     * @return
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 获取展示的label
     *
     * @return
     */
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("label", label)
                .toString();
    }

    /**
     * 获取value的枚举
     *
     * @param value String
     * @return
     */
    public static EmailType getEnum(String value) {
        return enumMap.get(value);
    }

}