package com.wj.spc.demo_1203.domain.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ShengchanXiaofei02 {

    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    /*
        通用方法参数传接口类型，可以通用
     */
    public ShengchanXiaofei02(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        //当前传进来的是哪个实现类，做个日志记录
        System.out.println(blockingQueue.getClass().getName());
    }

    public void prod() throws InterruptedException {
        String data = "";
        boolean status;
        while (flag){
            data = String.valueOf(atomicInteger.getAndAdd(2));
            status = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (status){
                System.out.println(Thread.currentThread().getName() + "插入队列" + "【" + data + "】成功！");
            }else{
                System.out.println(Thread.currentThread().getName() + "插入队列" + "【" + data + "】失败！");
            }
            TimeUnit.SECONDS.sleep(1);//控制生产速度，一秒钟生产一个
        }
        System.out.println(Thread.currentThread().getName() + "停止工作了！");
    }

    public void consumer() throws InterruptedException {
        String data = "";
        while (flag){
            data = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == data || "".equals(data)){
                flag = false;
                System.out.println(Thread.currentThread().getName() + "超过两秒没有得到元素，退出线程！");
                //return;
            }else{
                System.out.println(Thread.currentThread().getName() + "消费【" + data + "】成功!");
            }
        }
        System.out.println(Thread.currentThread().getName() + "停止工作！");

    }

    public void stop(){
        this.flag = false;
    }
}
