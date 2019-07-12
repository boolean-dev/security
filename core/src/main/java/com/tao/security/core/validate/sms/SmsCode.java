package com.tao.security.core.validate.sms;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @ClassName SmsCode
 * @Descriiption 手机验证码
 * @Author yanjiantao
 * @Date 2019/7/12 16:31
 **/
@Data
public class SmsCode {

    private String phone;

    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public SmsCode(String phone, String code, Integer expireIn) {
        this.phone = phone;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusMinutes(expireIn);
    }
}
