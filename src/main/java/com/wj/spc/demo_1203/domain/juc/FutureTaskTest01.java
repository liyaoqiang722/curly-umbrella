package com.wj.spc.demo_1203.domain.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest01 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        for (int i = 1;i<= 5;i++){
            System.out.println(i + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(1);
        }
        return 123;
    }
}
