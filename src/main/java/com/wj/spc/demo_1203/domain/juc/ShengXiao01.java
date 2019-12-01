package com.wj.spc.demo_1203.domain.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShengXiao01 {

    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void add(){
        lock.lock();
        try{
            while (0 != number){
                condition.await();
                condition.notify();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "：" + number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void dec(){
        lock.lock();
        try{
            while (0 == number){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "：" + number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
