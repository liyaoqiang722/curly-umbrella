package com.wj.spc.demo_1203.dao.redis;

import com.wj.spc.demo_1203.domain.User;

import java.util.List;

public interface TestDao {

    List<User> getUsers();

    void saveUsers(List<User> users);
}
