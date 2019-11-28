package com.wj.spc.demo_1203.controller;

import com.wj.spc.demo_1203.domain.Province;
import com.wj.spc.demo_1203.domain.User;
import com.wj.spc.demo_1203.juc.FutureTaskTest01;
import com.wj.spc.demo_1203.juc.ShengXiao01;
import com.wj.spc.demo_1203.juc.ShengchanXiaofei02;
import com.wj.spc.demo_1203.juc.SiSuoTest01;
import com.wj.spc.demo_1203.servuice.TestService;
import com.wj.spc.demo_1203.viewModel.Result;
import com.wj.spc.demo_1203.viewModel.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequestMapping("/test")
public class JucTestController {

    @Resource
    private TestService testService;

    @RequestMapping(value = "/test01",
            method = RequestMethod.GET)
    public String test01(){
        return "Welcome to the demo1203!";
    }


    /**
     * redis 测试
     * @return
     */
    @RequestMapping(value = "/test02",
            method = RequestMethod.GET)
    public Object test02(){
        List<User> user = testService.dataSource1();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);
        successResponse.setData(user);

        return successResponse;
    }

    @RequestMapping(value = "/test03",
            method = RequestMethod.GET)
    public Object test03(){
        User user = testService.dataSource2();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);
        successResponse.setData(user);

        return successResponse;
    }

    /**
     * DiscoveryClient可以注入从注册中心获取的服务相关信息。
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/test04",
            method = RequestMethod.GET)
    public Object test04(){
        List<Province> user = testService.dataSource3();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);
        successResponse.setData(user);

        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("service1");

        return successResponse;
    }

    /**
     * EnumSet。
     */
    @RequestMapping(value = "/enumSet01",
            method = RequestMethod.GET)
    public Object enumSetTest01(){
        testService.enumSetTest01();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);

        return successResponse;
    }

    /**
     * word Test
     */
    @RequestMapping(value = "/wordTest01",
            method = RequestMethod.GET)
    public void wordTest01(HttpServletResponse response) throws Exception{
        ServletOutputStream out = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = testService.getWordForJudgement();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.addHeader("Content-Disposition", "attachment;filename=judgement.docx");
            out = response.getOutputStream();
            FileCopyUtils.copy(byteArrayInputStream, out);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Pattern Test
     */
    @RequestMapping(value = "/patternTest01",
            method = RequestMethod.GET)
    public void patternTest01() {
        testService.patternTest01();
    }

    /**
     * kafka Test
     */
    @RequestMapping(value = "/kafkaTest01",
            method = RequestMethod.GET)
    public void kafkaTest01() {
        testService.kafkaTest01();
    }

    /**
     * 生产者消费者示例，两个线程，一个对 number 加 1，一个减 1，循环操作 5 次
     * @param args
     * @throws InterruptedException
     */
    /*public static void main(String[] args ) {
        *//*
            thread1：1
            thread2：0
            thread1：1
            thread2：0
            thread1：1
            thread2：0
         *//*
        ShengXiao01 shengXiao01 = new ShengXiao01();
        new Thread(() -> {
            for (int i = 1;i<=3;i++){
                shengXiao01.add();
            }
        },"thread1").start();
        new Thread(() -> {
            for (int i = 1;i<=3;i++){
                shengXiao01.dec();
            }
        },"thread2").start();
    }*/


    /**
     * 生产者消费者完整版（阻塞队列版）
     * @param args
     */
    /*public static void main(String[] args ) {
        ShengchanXiaofei02 shengchanXiaofei02 = new ShengchanXiaofei02(new ArrayBlockingQueue<>(5));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "启动！");
            try {
                shengchanXiaofei02.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "启动！");
            try {
                TimeUnit.SECONDS.sleep(5);
                shengchanXiaofei02.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        shengchanXiaofei02.stop();
        System.out.println(Thread.currentThread().getName() + "停止！");
    }*/


    /**
     * 三个线程，A，B，C，A先执行完后，B再执行，B执行完后，C再执行（定点唤醒）
     * @param args
     */
    /*public static void main(String[] args ) {

    }*/

    /**
     * FutureTask
     *
     * Java 中 Thread 的构造方法的参数中，可以支持 Runnable，但是并没有 Callable 类型的参数，那么通过 Callable 如何创建线程呢？
     * FutureTask 类实现了 RunnableFuture 接口，RunnableFuture 继承了 Runnable 和 Future<V>
     * FutureTask 拥有构造方法：public FutureTask(Callable<V> callable)
     * 所以 FutureTask 完美的将 Callable 和 Runnable 进行了转换
     * @param args
     */
    /*public static void main(String[] args ) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new FutureTaskTest01());
        new Thread(futureTask,"Thread-1").start();

        TimeUnit.SECONDS.sleep(3);

       // Integer task = (Integer)futureTask.get();
        while (!futureTask.isDone()){

        }
        System.out.println(123);
    }*/


    /**
     * 线程池
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void xcc(String[] args ) throws ExecutionException, InterruptedException {
        // 固定容量 线程池 Executors.newFixedThreadPool
        /*
            执行结果：
            pool-1-thread-1执行！
            pool-1-thread-2执行！
            pool-1-thread-2执行！
            pool-1-thread-2执行！
            pool-1-thread-2执行！
            pool-1-thread-2执行！
            pool-1-thread-2执行！
            pool-1-thread-3执行！
         */
        // 表明 线程池 中的线程是可以复用的，最大只是 3
        /*ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        try {
            //模拟 8 个线程，线程池容量是 3 的情况；
            for (int i = 0;i<8;i++){
                newFixedThreadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行！");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 注意：线程池使用完后，一定要进行销毁！！！
            newFixedThreadPool.shutdown();
        }*/

        /*
        ============================================================================================
         */

        // 只有一个线程的线程池 Executors.newSingleThreadExecutor
        /*
            执行结果：
            pool-1-thread-1执行！
            pool-1-thread-1执行！
            pool-1-thread-1执行！
            pool-1-thread-1执行！
            pool-1-thread-1执行！
            pool-1-thread-1执行！
         */
        // 表明 线程池 中的线程是可以复用的，只有一个线程
        /*ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        try {
            //模拟 6 个线程，线程池容量是 1 的情况；
            for (int i = 0;i<6;i++){
                newSingleThreadExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行！");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 注意：线程池使用完后，一定要进行销毁！！！
            newSingleThreadExecutor.shutdown();
        }*/

        /*
        ============================================================================================
         */

        // 动态扩容的线程池 Executors.newCachedThreadPool
        /*
            执行结果：
            pool-1-thread-2执行！
            pool-1-thread-1执行！
            pool-1-thread-3执行！
            pool-1-thread-4执行！
            pool-1-thread-5执行！
            pool-1-thread-6执行！
         */
        // 此处显示是 6 个线程在处理，如果有空置线程，数量会减少，例如在 System.out 前加上 sleep ，模拟线程有足够时间处理
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        try {
            //模拟 6 个线程，线程池容量是 1 的情况；
            for (int i = 0;i<6;i++){
                newCachedThreadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行！");
                });
                try {
                    TimeUnit.NANOSECONDS.sleep(1); // 1毫秒
                    /*
                        结果为：
                        pool-1-thread-1执行！
                        pool-1-thread-1执行！
                        pool-1-thread-1执行！
                        pool-1-thread-1执行！
                        pool-1-thread-1执行！
                        pool-1-thread-1执行！
                     */
                    // 线程池 只有一个线程在处理
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 注意：线程池使用完后，一定要进行销毁！！！
            newCachedThreadPool.shutdown();
        }
    }


    /**
     * 死锁示例
     * @param args
     */
    public static void main(String[] args ) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new SiSuoTest01(lockA, lockB), "Thread-A").start();
        new Thread(new SiSuoTest01(lockB, lockA), "Thread-B").start();
    }
}
