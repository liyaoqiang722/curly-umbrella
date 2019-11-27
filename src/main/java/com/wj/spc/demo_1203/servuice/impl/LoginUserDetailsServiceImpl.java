package com.wj.spc.demo_1203.servuice.impl;

import com.wj.spc.demo_1203.domain.User;
import com.wj.spc.demo_1203.servuice.LoginUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by wangqiliang on 17/4/14.
 */
@Service
public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {

    @Override
    public UserDetails getUserByUsername(String username) {
        User user = new User();
        user.setId("1");
        user.setPhoneNumber("17637925856");
        user.setPassword("123");
        user.setStatus("1");

        return user;
    }
}
