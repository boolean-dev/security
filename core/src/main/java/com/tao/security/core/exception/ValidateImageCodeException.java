package com.tao.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @ClassName ValidateImageCodeException
 * @Descriiption 图形验证码验证异常
 * @Author yanjiantao
 * @Date 2019/7/12 14:10
 **/
public class ValidateImageCodeException extends AuthenticationException {
    public ValidateImageCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateImageCodeException(String msg) {
        super(msg);
    }
}
