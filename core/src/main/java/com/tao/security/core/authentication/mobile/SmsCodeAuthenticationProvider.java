package com.tao.security.core.authentication.mobile;

import com.tao.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;

/**
 * @ClassName SmsCodeAuthenticationProvider
 * @Descriiption TODO
 * @Author yanjiantao
 * @Date 2019/7/12 17:52
 **/
@Data
public class SmsCodeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Override
    public boolean supports(Class<?> authentication) {
        return  SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(user == null) {
            throw new InternalAuthenticationServiceException("未获取到用户信息");
        }

        return user;
    }
}
