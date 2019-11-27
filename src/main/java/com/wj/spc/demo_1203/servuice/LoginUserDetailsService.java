package com.wj.spc.demo_1203.servuice;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by wangqiliang on 17/4/14.
 */
public interface LoginUserDetailsService {


    UserDetails getUserByUsername(String username);
}
