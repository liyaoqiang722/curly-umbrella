package com.wj.spc.demo_1203.servuice.impl;

import com.wj.spc.demo_1203.dao.mapper.aMapper.AMapper;
import com.wj.spc.demo_1203.dao.mapper.aMapper.BaseRegionAMapper;
import com.wj.spc.demo_1203.dao.mapper.bMapper.BMapper;
import com.wj.spc.demo_1203.dao.mapper.bMapper.BaseRegionBMapper;
import com.wj.spc.demo_1203.dao.redis.TestDao;
import com.wj.spc.demo_1203.domain.Province;
import com.wj.spc.demo_1203.domain.User;
import com.wj.spc.demo_1203.servuice.TestService;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private AMapper aMapper;

    @Resource
    private BaseRegionAMapper baseRegionAMapper;

    @Resource
    private BMapper bMapper;

    @Resource
    private BaseRegionBMapper baseRegionBMapper;

    @Autowired
    private TestDao testDao;

    @Override
    public List<User> dataSource1() {

        List<User> users = new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间

        try {
            users = testDao.getUsers();
            if (users == null || users.size() == 0) {
                users = aMapper.selectAllUser();
                testDao.saveUsers(users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();    //获取结束时间
        log.info("程序运行时间：" + (endTime - startTime) + "ms");
        return users;
    }

    @Override
    public User dataSource2() {
        return bMapper.selectAllUser();
    }

    @Override
    public List<Province> dataSource3() {

        long startTime = System.currentTimeMillis();    //获取开始时间

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Task task = new Task();
        executorService.submit(task);
        executorService.shutdown();

        List<Province> provinces = baseRegionAMapper.getProvince();

        long endTime = System.currentTimeMillis();    //获取结束时间
        log.info("程序运行时间：" + (endTime - startTime) + "ms");
        return provinces;
    }

    private class Task implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(6000);
                baseRegionBMapper.getProvince();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
