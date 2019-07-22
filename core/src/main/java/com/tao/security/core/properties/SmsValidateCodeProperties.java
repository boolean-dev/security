package com.tao.security.core.properties;

import lombok.Data;

/**
 * @ClassName SmsValidateCodeProperties
 * @Descriiption 短信验证码配置类
 * @Author yanjiantao
 * @Date 2019/7/22 11:47
 **/

@Data
public class SmsValidateCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;

    /**
     * 验证码过期时间
     */
    private long expireIn = 60L;

    /**
     * 添加验证码url
     */
    private String url;
}
