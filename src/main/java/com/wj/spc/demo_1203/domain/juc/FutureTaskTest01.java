package com.wj.spc.demo_1203.domain.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest01 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("FutureTask调用！");
        return 123;
    }
}
