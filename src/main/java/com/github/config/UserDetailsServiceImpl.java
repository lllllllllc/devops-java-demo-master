package com.github.config;

import com.github.vo.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //以下过程主要模拟从数据库中获取用户信息
        if ("admin".equals(username)) {
            return new SysUser("admin", "123456", null);
        } else if ("zhangsan".equals(username )) {
            return new SysUser("zhangsan", "123456", null);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}