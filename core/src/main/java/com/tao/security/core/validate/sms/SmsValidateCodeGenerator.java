package com.tao.security.core.validate.sms;

import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.validate.handle.ValidateCodeGenerator;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import static com.tao.security.core.properties.SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;

/**
 * @ClassName SmsValidateCodeGenerator
 * @Descriiption 短信生成器
 * @Author yanjiantao
 * @Date 2019/7/22 18:04
 **/
@Data
@Component("smsValidateCodeGenerator")
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public SmsValidateCode generate(ServletWebRequest request) {
        String phone = request.getParameter(DEFAULT_PARAMETER_NAME_MOBILE);

        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new SmsValidateCode(code, securityProperties.getCode().getSms().getExpireIn(), phone);
    }
}
