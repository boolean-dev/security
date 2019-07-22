package com.tao.security.core.validate.sms;

import com.tao.security.core.validate.ValidateCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @ClassName SmsValidateCode
 * @Descriiption 手机验证码
 * @Author yanjiantao
 * @Date 2019/7/12 16:31
 **/

@Data
@EqualsAndHashCode(callSuper = true)
public class SmsValidateCode extends ValidateCode{

    private String phone;

    public SmsValidateCode(String code, long expireIn, String phone) {
        super(code, expireIn);
        this.phone = phone;
    }
}
