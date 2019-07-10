package com.tao.security.core.config;

import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.security.result.MyAuthenctiationFailureHandler;
import com.tao.security.core.security.result.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName WebSecurityConfig
 * @Descriiption Spring Security配置类
 * @Author yanjiantao
 * @Date 2019/7/8 10:45
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())
                .loginProcessingUrl("/user/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

}
