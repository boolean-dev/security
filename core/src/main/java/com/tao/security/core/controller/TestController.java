package com.tao.security.core.controller;

import com.tao.security.core.utils.ImageCodeUtils;
import com.tao.security.core.validate.image.ImageValidateCode;
import com.tao.security.core.validate.sms.SmsValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestController
 * @Descriiption 测试Controller
 * @Author yanjiantao
 * @Date 2019/7/26 8:54
 **/

@Slf4j
@Controller
public class TestController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    public static final String SESSION_SMS_KEY = "SESSION_KEY_SMS_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @ResponseBody
    @GetMapping("/user/{id}")
    public Object user(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        return result;

    }

    @GetMapping("/user/login/code/image")
    @ResponseBody
    public void codeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        ImageValidateCode imageCode = ImageCodeUtils.generate(webRequest);
        sessionStrategy.setAttribute(webRequest, SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/user/login/code/sms")
    @ResponseBody
    public void codeSms(String phone, HttpServletRequest request) throws IOException {

        ServletWebRequest webRequest = new ServletWebRequest(request);

        String code = RandomStringUtils.randomNumeric(4);
        SmsValidateCode smsCode = new SmsValidateCode(code, 30, phone);
        log.info("发送短信，phone={},code={}", phone, code);
        sessionStrategy.setAttribute(webRequest, SESSION_SMS_KEY, smsCode);
    }


}
