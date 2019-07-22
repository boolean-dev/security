package com.tao.security.core.properties;

import lombok.Data;

/**
 * @ClassName ValidateCodeProperties
 * @Descriiption 验证码配置类
 * @Author yanjiantao
 * @Date 2019/7/22 11:45
 **/

@Data
public class ValidateCodeProperties {
    private ImageValidateCodeProperties image = new ImageValidateCodeProperties();
    private SmsValidateCodeProperties sms = new SmsValidateCodeProperties();
}
