package com.tao.security.core.config;

import com.tao.security.core.authentication.AbstractChannelSecurityConfig;
import com.tao.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.tao.security.core.properties.SecurityConstants;
import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.security.result.MyAuthenticationFailureHandler;
import com.tao.security.core.security.result.MyAuthenticationSuccessHandler;
import com.tao.security.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName WebSecurityConfig
 * @Descriiption Spring Security配置类
 * @Author yanjiantao
 * @Date 2019/7/8 10:45
 **/
@Configuration
public class WebSecurityConfig extends AbstractChannelSecurityConfig {


    @Autowired
    private SecurityProperties securityProperties;


    @Autowired
    protected MyAuthenticationFailureHandler failureHandler;

    @Autowired
    protected MyAuthenticationSuccessHandler successHandler;


    @Autowired
    protected SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    protected ValidateCodeSecurityConfig validateCodeSecurityConfig;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        super.applyPasswordAuthenticationConfig(http);
        http.apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/**")
                    .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .csrf().disable();
    }

}
