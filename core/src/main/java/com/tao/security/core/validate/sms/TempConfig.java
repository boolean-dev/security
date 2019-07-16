package com.tao.security.core.validate.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 * @ClassName TempConfig
 * @Descriiption TODO
 * @Author yanjiantao
 * @Date 2019/7/15 17:00
 **/
@Component
public class TempConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        ValidateSmsCodeFilter smsCodeAuthenticationFilter = new ValidateSmsCodeFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(imoocAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);

        SmsCodeAuthenticationProvider authenticationProvider = new SmsCodeAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);

    }
}
