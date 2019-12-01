package com.wj.spc.demo_1203.controller;

import com.wj.spc.demo_1203.domain.shejimoshi.Singletion01;
import com.wj.spc.demo_1203.domain.shejimoshi.Singletion08;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SheJiMoShiController {

    /**
     * 单例：
     *      1、饿汉式（静态常量） Singletion01
     *      2、饿汉式（静态变量） Singletion02 优缺点同 Singletion01
     *      3、懒汉式（线程不安全） Singletion03 实际开发中不可用
     *      4、懒汉式（线程安全，同步方法，getInstance 层面加锁） Singletion04 实际开发中不可用
     *      5、懒汉式（线程不安全，同步代码块） Singletion05 实际开发中不可用
     *      6、双重检查  Singletion06  推荐使用
     *      7、静态内部类  Singletion07  推荐使用
     *      8、枚举  Singletion07  推荐使用
     * @param args
     */
    public static void danLi(String[] args){
        // 1、饿汉式（静态常量）
        Singletion01 singletion0101 = Singletion01.getInstance();
        Singletion01 singletion0102 = Singletion01.getInstance();
        System.out.println(singletion0101 == singletion0102);
        System.out.println(singletion0101.hashCode());
        System.out.println(singletion0101.hashCode());

        // 8、枚举
        Singletion08 instance1 = Singletion08.INSTANCE;
        Singletion08 instance2 = Singletion08.INSTANCE;
        System.out.println(instance1 == instance2);
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
    }


}
