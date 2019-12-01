package com.wj.spc.demo_1203.domain.shejimoshi;

import lombok.Synchronized;

/**
 * 静态内部类
 *
 * 优点：
 *      采用类装载的机制保证初始化实例只有一个线程
 *      在类装载时不会实例化，而是调用 getInstance 方法才会装载 Singletion07Instance 类
 *      类的静态属性只会在第一次加载类的时候初始化，通过 JVM 保证了线程安全
 */
public class Singletion07 {

    // 2、本类内部创建对象实例
    private static volatile Singletion07 instance;

    // 1、构造器私有化，防止外部创建
    private Singletion07(){

    }

    // 静态内部类，该类中有一个静态属性
    private static class Singletion07Instance {
        private static final Singletion07 INSTANCE = new Singletion07();
    }

    // 3、提供一个公有的静态方法，双重检测，解决线程安全问题，同时保证效率
    public static Singletion07 getInstance(){
        return Singletion07Instance.INSTANCE;
    }

}
