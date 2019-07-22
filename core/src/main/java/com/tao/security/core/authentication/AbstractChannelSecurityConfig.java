package com.tao.security.core.authentication;

import com.tao.security.core.properties.SecurityConstants;
import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.security.result.MyAuthenticationFailureHandler;
import com.tao.security.core.security.result.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName AbstractChannelSecurityConfig
 * @Descriiption SpringSecurity基础配置类
 * @Author yanjiantao
 * @Date 2019/7/22 14:44
 **/
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyAuthenticationFailureHandler failureHandler;

    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler);
    }

}
