package com.github.config;

import com.github.util.UserAccessDeniedHandler;
import com.github.util.UserAuthenticationEntryPoint;
import com.github.util.UserLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //未登录处理类
    @Autowired
    private UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    //无授权处理类
    @Autowired
    private UserAccessDeniedHandler userAccessDeniedHandler;

    //无授权处理类
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    //登出成功处理类
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用csrf
                .csrf()
                .disable()

                //禁用默认的登录
                .formLogin()
                .disable()

                //用户登出
                .logout()
                .logoutSuccessHandler(userLogoutSuccessHandler)//登出成功时的处理

                //异常处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(userAccessDeniedHandler)//权限不足时的处理
                .authenticationEntryPoint(userAuthenticationEntryPoint)//未登录时的处理

                //请求过滤
                .and()
                .authorizeRequests()
                .antMatchers("/login").anonymous()//登录请求，只允许匿名访问
                .anyRequest().authenticated();//除此之外，其他请求均需要登录后才可以访问
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userAuthenticationProvider);
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}