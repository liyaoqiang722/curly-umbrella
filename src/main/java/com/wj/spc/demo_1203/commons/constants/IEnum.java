package com.wj.spc.demo_1203.commons.constants;

/**
 * 本系统所有枚举实现的接口 规范 value, label 用于MyBatis枚举映射
 * Created by liyaoqiang on 2018/11/16.
 *
 * @param <E>
 * @param <T>
 */
public interface IEnum<E extends Enum<?>, T> {
    /**
     * 获取值
     * @return
     */
    T getValue();

    /**
     * 获取展示的label
     * @return
     */
    String getLabel();

}
