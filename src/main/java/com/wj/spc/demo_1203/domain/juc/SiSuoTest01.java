package com.wj.spc.demo_1203.domain.juc;

import java.util.concurrent.TimeUnit;

public class SiSuoTest01 implements Runnable{

    private String lockA;
    private String lockB;

    public SiSuoTest01(String lockA, String lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "持有锁：" + lockA + "获取锁：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "持有锁：" + lockB + "获取锁：" + lockA);
            }
        }
    }
}
