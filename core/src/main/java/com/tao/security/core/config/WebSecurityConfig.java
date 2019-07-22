package com.tao.security.core.config;

import com.tao.security.core.authentication.AbstractChannelSecurityConfig;
import com.tao.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    /*@Autowired
    private UserDetailsService userDetailsService;*/


    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        super.applyPasswordAuthenticationConfig(http);
        // 图形验证码拦截器
   /*     ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);
//
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);*/


        /*http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .apply(tempConfig)
            .and()
            .authorizeRequests()
            .antMatchers(securityProperties.getBrowser().getLoginPage(), "/user/login/code/image", "/user/login/code/sms","/authentication/phone")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();*/



//        http.apply(tempConfig);

        // 图形验证码拦截器执行

//        SmsCodeAuthenticationProvider authenticationProvider = new SmsCodeAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        http.authenticationProvider(authenticationProvider)
//                .addFilterAfter(smsCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
//        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
