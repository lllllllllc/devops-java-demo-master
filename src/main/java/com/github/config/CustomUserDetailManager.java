//package com.github.config;
//
//import com.github.vo.CustomUser;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.log.LogMessage;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsPasswordService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.stereotype.Service;
//
//import java.security.Provider;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class CustomUserDetailManager implements UserDetailsManager, UserDetailsPasswordService {
//
//    /**
//     * 这里用Map来模拟数据库操作，实际开发中可替换成对应的Dao
//     */
//    private Map<String, CustomUser> users = new HashMap<>();
//
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public void createUser(UserDetails user) {
//        users.putIfAbsent(user.getUsername(), new CustomUser(user));
//    }
//
//    @Override
//    public void updateUser(UserDetails user) {
//        users.put(user.getUsername(), new CustomUser(user));
//    }
//
//    @Override
//    public void deleteUser(String username) {
//        users.remove(username);
//    }
//
//    /**
//     * 修改密码：参考 InMemoryUserDetailsManager
//     */
//    @Override
//    public void changePassword(String oldPassword, String newPassword) {
//        //获取当前登录用户
//        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//        if (currentUser == null) {
//            // This would indicate bad coding somewhere
//            throw new AccessDeniedException(
//                    "Can't change password as no Authentication object found in context " + "for current user.");
//        }
//        String username = currentUser.getName();
//        if (this.authenticationManager != null) {
//            //判断密码是否匹配
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
//        } else {
//            log.debug("No authentication manager set. Password won't be re-checked.");
//        }
//        CustomUser user = (CustomUser) users.get(username);
//        if (user == null) {
//            throw new IllegalStateException("当前用户不存在");
//        }
//        user.setPassword(newPassword);
//    }
//
//    @Override
//    public boolean userExists(String username) {
//        return users.containsKey(username);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return users.get(username);
//    }
//
//    @Override
//    public UserDetails updatePassword(UserDetails user, String newPassword) {
//        CustomUser customUser = users.get(user.getUsername());
//        customUser.setPassword(newPassword);
//        return customUser;
//    }
//}
