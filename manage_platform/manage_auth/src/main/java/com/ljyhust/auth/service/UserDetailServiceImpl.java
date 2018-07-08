package com.ljyhust.auth.service;

import com.ljyhust.auth.entity.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/7/7.
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("root");
        userInfo.setPassword("admin");
        return userInfo;
    }
}
