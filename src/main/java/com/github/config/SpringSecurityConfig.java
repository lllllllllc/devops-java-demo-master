//package com.github.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private CustomUserDetailManager userDetailManager;
//
//    @Autowired
//    private CustomUserService customUserService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    /**
//     * 自定义身份认证
//     * 该配置类的用于用户的认证
//     * 1、提供身份信息（用户名、密码、角色、权限）
//     * 2、配置认证信息的存储方式：内存、数据库
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //方案一：基于内存的存储方式
//        /*auth.inMemoryAuthentication().withUser("hardy").password(passwordEncoder.encode("123456")).roles("admin");*/
//
//        //方案二：实现UserDetailsManager
//
//        //为方便测试，直接创建一个用户
//        /*UserDetails user = User.withUsername("hardy").password(passwordEncoder.encode("123456"))
//                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin")).build();
//        userDetailManager.createUser(user);
//        //将我们自定义的用户管理器配置到SpringSecurity中
//        auth.userDetailsService(userDetailManager);*/
//
//        //方案三：实现UserDetailService
//        auth.userDetailsService(customUserService);
//    }
//
//    /**
//     * 资源权限配置（过滤器链）
//     * 1、配置被拦截的资源
//     * 2、拦截对应的角色权限（即拥有某个权限才可以访问）
//     * 3、定义认证方式：httpBasic或者httpForm
//     * 4、自定义登录界面、登录请求地址、错误处理方式等
//     * 5、自定义SpringSecurity过滤器
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //采用HttpBasic认证
//        http.formLogin()
//                //3、自定义登录界面
//                .loginPage("/login/toLogin")
//                //用户名的参数名
//                .usernameParameter("usrename")
//                //密码的参数名
//                .passwordParameter("password")
//                //登录请求的地址(默认是/login)
//                .loginProcessingUrl("/login/doLogin")
//                //登录成功后转发到(这里是Post请求，因为是“转发”，不适重定向)
//                .successForwardUrl("/test/hello")
//                //登录失败
//                .failureForwardUrl("/login/fail")
//                .and()
//                .authorizeRequests()
//                //对于login请求直接放开
//                .antMatchers("/login/toLogin").permitAll()
//                //所有请求
//                .anyRequest()
//                //都需要认证
//                .authenticated()
//                .and().csrf().disable()
//        ;
//    }
//
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//    }
//}