package com.tao.security.core.validate.sms;

/**
 * @ClassName SmsCodeProcessor
 * @Descriiption 短信接口
 * @Author yanjiantao
 * @Date 2019/7/22 18:13
 **/
public interface SmsCodeSender {
    /**
     * 发送短信
     * @param mobile    电话
     * @param code  code
     */
    void send(String mobile, String code);
}
