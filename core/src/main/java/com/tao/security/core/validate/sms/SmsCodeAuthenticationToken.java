package com.tao.security.core.validate.sms;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName SmsCodeAuthenticationToken
 * @Descriiption sms登录信息
 * @Author yanjiantao
 * @Date 2019/7/12 17:40
 **/
public class SmsCodeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public SmsCodeAuthenticationToken(Object principal) {
        super(principal, null);
    }
    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SmsCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
