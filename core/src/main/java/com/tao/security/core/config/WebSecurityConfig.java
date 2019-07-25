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
public class WebSecurityConfig /*extends AbstractChannelSecurityConfig*/ extends WebSecurityConfigurerAdapter {

    /*@Autowired
    private UserDetailsService userDetailsService;*/

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



        http.formLogin()
                .loginPage("/user/signIn")
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX,
                        "/user/login/code/image",
                        "/index11111",
                        "/user/signIn")
                .permitAll()
                .anyRequest()
                .authenticated();


       /* super.applyPasswordAuthenticationConfig(http);
        http.apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX,
                        "/user/login/code/image")
                    .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .csrf().disable();*/
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
