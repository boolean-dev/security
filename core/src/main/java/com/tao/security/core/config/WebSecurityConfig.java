package com.tao.security.core.config;

import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.security.result.MyAuthenctiationFailureHandler;
import com.tao.security.core.security.result.MyAuthenticationSuccessHandler;
import com.tao.security.core.validate.image.ValidateCodeFilter;
import com.tao.security.core.validate.sms.SmsCodeAuthenticationProvider;
import com.tao.security.core.validate.sms.SmsCodeFilter;
import com.tao.security.core.validate.sms.ValidateSmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @ClassName WebSecurityConfig
 * @Descriiption Spring Security配置类
 * @Author yanjiantao
 * @Date 2019/7/8 10:45
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

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

        // 图形验证码拦截器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);

//        ValidateSmsCodeFilter validateSmsCodeFilter = new ValidateSmsCodeFilter();
//        validateSmsCodeFilter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);

        SmsCodeFilter filter = new SmsCodeFilter();
        filter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);

        // 图形验证码拦截器执行

        SmsCodeAuthenticationProvider authenticationProvider = new SmsCodeAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(filter, AbstractPreAuthenticatedProcessingFilter.class);

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())
                .loginProcessingUrl("/user/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage(), "/user/login/code/image", "/user/login/code/sms")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

}
