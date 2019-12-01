package com.wj.spc.demo_1203.domain.shejimoshi;

/**
 * 懒汉式（线程不安全，同步代码块）
 *
 * 优点：
 *      做到了懒加载，但是线程不安全，但是实际开发中不可用
 * 缺点：
 *      null == instance 做不到线程安全
 */
public class Singletion05 {

    // 1、构造器私有化，防止外部创建
    private Singletion05(){

    }

    // 2、本类内部创建对象实例
    private static Singletion05 instance;

    // 3、提供一个公有的静态方法，当使用到该方法时，加锁，创建实例
    public static Singletion05 getInstance(){
        if (null == instance){
            synchronized (Singletion05.class){
                instance = new Singletion05();
            }
        }
        return instance;
    }

}
