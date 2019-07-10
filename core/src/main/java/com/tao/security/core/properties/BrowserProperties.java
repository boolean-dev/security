package com.tao.security.core.properties;

import lombok.Data;

/**
 * @ClassName BrowserProperties
 * @Descriiption 浏览器配置
 * @Author yanjiantao
 * @Date 2019/7/10 16:31
 **/
@Data
public class BrowserProperties {
    private String  loginPage = "/user/signIn";
}
