package com.tao.security.core.validate.handle;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @ClassName ValidateCodeProcessor
 * @Descriiption 验证码处理器，处理不同的验证码登录
 * @Author yanjiantao
 * @Date 2019/7/22 15:37
 **/
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     *
     * @param request the request
     * @throws Exception    异常
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest the request
     */
    void validate(ServletWebRequest servletWebRequest);
}
