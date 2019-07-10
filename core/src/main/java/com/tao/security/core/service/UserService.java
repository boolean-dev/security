package com.tao.security.core.service;

import com.tao.security.core.entity.User;
import com.tao.security.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Descriiption UserService
 * @Author yanjiantao
 * @Date 2019/7/10 14:02
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
