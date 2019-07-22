package com.tao.security.core.validate.handle;

import com.tao.security.core.validate.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @ClassName ValidateCodeGenerator
 * @Descriiption 验证码生成器
 * @Author yanjiantao
 * @Date 2019/7/22 16:39
 **/
public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     * @param request   the request
     * @return  验证码
     */
    ValidateCode generate(ServletWebRequest request);
}
