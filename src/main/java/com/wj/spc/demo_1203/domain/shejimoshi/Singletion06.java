package com.wj.spc.demo_1203.domain.shejimoshi;

/**
 * 双重检查
 *
 * 优点：
 *      双重检测的理念在多线程开发中经常用到，保证了线程安全
 *      只会一次实例化，保证了效率
 */
public class Singletion06 {

    // 1、构造器私有化，防止外部创建
    private Singletion06(){

    }

    // 2、本类内部创建对象实例
    private static volatile Singletion06 instance;

    // 3、提供一个公有的静态方法，双重检测，解决线程安全问题，同时保证效率
    public static Singletion06 getInstance(){
        if (null == instance){
            synchronized (Singletion06.class){
                if (null == instance){
                    instance = new Singletion06();
                }
            }
        }
        return instance;
    }

}
