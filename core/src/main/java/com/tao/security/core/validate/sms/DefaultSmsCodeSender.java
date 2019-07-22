package com.tao.security.core.validate.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName DefaultSmsCodeSender
 * @Descriiption TODO
 * @Author yanjiantao
 * @Date 2019/7/22 18:15
 **/

@Slf4j
@Component
public class DefaultSmsCodeSender implements  SmsCodeSender{

    @Override
    public void send(String mobile, String code) {
        log.info("向手机号phone={},发送短信验证码code={}", mobile, code);
        System.out.println("向手机"+mobile+"发送短信验证码"+code);
    }
}
