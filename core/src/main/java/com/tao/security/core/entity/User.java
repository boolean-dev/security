package com.tao.security.core.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName User
 * @Descriiption 用户
 * @Author yanjiantao
 * @Date 2019/7/10 13:56
 **/
@Data
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
}
