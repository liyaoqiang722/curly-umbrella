package com.wj.spc.demo_1203.servuice;

import com.wj.spc.demo_1203.domain.Province;
import com.wj.spc.demo_1203.domain.User;

import java.util.List;

public interface TestService {

    List<User> dataSource1();

    User dataSource2();

    List<Province> dataSource3();

    void enumSetTest01();
}
