package com.tao.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @ClassName ValidateCodeException
 * @Descriiption 验证码校验异常
 * @Author yanjiantao
 * @Date 2019/7/22 17:03
 **/
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
