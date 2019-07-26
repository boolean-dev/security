package com.tao.security.core.validate.handle;

import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.validate.image.ImageValidateCodeGenerator;
import com.tao.security.core.validate.sms.DefaultSmsCodeSender;
import com.tao.security.core.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName ValidateCodeBeanConfig
 * @Descriiption TODO
 * @Author yanjiantao
 * @Date 2019/7/25 16:59
 **/
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageValidateCodeGenerator codeGenerator = new ImageValidateCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
