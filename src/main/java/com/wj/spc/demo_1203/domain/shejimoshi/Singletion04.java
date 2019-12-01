package com.wj.spc.demo_1203.domain.shejimoshi;

/**
 * 懒汉式（线程安全，同步方法，getInstance 层面加锁）
 *
 * 优点：
 *      做到了懒加载，解决了线程安全，但是实际开发中不可用
 * 缺点：
 *      效率太低，
 */
public class Singletion04 {

    // 1、构造器私有化，防止外部创建
    private Singletion04(){

    }

    // 2、本类内部创建对象实例
    private static Singletion04 instance;

    // 3、提供一个公有的静态方法，当使用到该方法时，加锁，创建实例
    public static synchronized Singletion04 getInstance(){
        if (null == instance){
            instance = new Singletion04();
        }
        return instance;
    }

}
