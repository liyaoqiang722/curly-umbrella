package com.wj.spc.demo_1203.domain.shejimoshi;

/**
 * 懒汉式（线程不安全）
 *
 * 优点：
 *      做到了懒加载，但是实际开发中不可用
 * 缺点：
 *      线程不安全；加入一个线程在创建实例的同时，另一个线程已经进入了 instance == null ，便会产生多个实例
 */
public class Singletion03 {

    // 1、构造器私有化，防止外部创建
    private Singletion03(){

    }

    // 2、本类内部创建对象实例
    private static Singletion03 instance;

    // 3、提供一个公有的静态方法，当使用到该方法时，创建实例
    public static Singletion03 getInstance(){
        if (null == instance){
            instance = new Singletion03();
        }
        return instance;
    }

}
