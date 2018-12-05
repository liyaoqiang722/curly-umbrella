package com.wj.spc.demo_1203.servuice.impl;

import com.wj.spc.demo_1203.dao.aMapper.AMapper;
import com.wj.spc.demo_1203.dao.bMapper.BMapper;
import com.wj.spc.demo_1203.domain.User;
import com.wj.spc.demo_1203.servuice.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private AMapper aMapper;

    @Resource
    private BMapper bMapper;

    @Override
    public List<User> dataSource1() {
        return aMapper.selectAllUser();
    }

    @Override
    public User dataSource2() {
        return bMapper.selectAllUser();
    }
}
