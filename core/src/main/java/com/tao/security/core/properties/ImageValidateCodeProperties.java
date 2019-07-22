package com.tao.security.core.properties;

import lombok.Data;

/**
 * @ClassName ImageValidateCodeProperties
 * @Descriiption 图形验证码配置类
 * @Author yanjiantao
 * @Date 2019/7/22 11:46
 **/

@Data
public class ImageValidateCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;

    /**
     * 验证码过期时间
     */
    private long expireIn = 60L;

    /**
     * 验证码长度
     */
    private int width = 60;

    /**
     * 验证码高度
     */
    private int height = 20;

    /**
     * 添加验证码url
     */
    private String url;
}
