package com.wj.spc.demo_1203.domain.shejimoshi;

/**
 * 饿汉式（静态常量）
 *
 * 优点：
 *      写法简单，在类创建的时候实例化，避免了线程同步问题，是一种可用的单例方式
 * 缺点：
 *      在类装载的时候就完成了实例化，如果一直都没有使用这个实例，就会造成内存资源浪费
 */
public class Singletion01 {

    // 1、构造器私有化，防止外部创建
    private Singletion01(){

    }

    // 2、本类内部创建对象实例
    private final static Singletion01 instance = new Singletion01();

    // 3、提供一个公有的静态方法，返回实例对象
    public static Singletion01 getInstance(){
        return instance;
    }

}
