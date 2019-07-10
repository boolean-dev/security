package com.tao.security.core.security;

import com.tao.security.core.entity.User;
import com.tao.security.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ClassName MyUserDetailsService
 * @Descriiption 自定义密码校验
 * @Author yanjiantao
 * @Date 2019/7/10 13:45
 **/
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("--------->登录用户名为:username={}", username);
        User user = userService.findByUserName(username);
        String password = passwordEncoder.encode(user.getPassword());
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
