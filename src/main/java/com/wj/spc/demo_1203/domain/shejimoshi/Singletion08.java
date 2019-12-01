package com.wj.spc.demo_1203.domain.shejimoshi;

/**
 * 枚举
 *
 * 优点：
 *      可以防止多线程问题
 *      可以防止反序列化重新创建新的对象
 */
public enum Singletion08 {

    INSTANCE;

    public void hello(){
        System.out.println("hello ~");
    }
}
