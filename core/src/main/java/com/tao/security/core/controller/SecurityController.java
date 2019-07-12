package com.tao.security.core.controller;

import com.tao.security.core.result.Result;
import com.tao.security.core.utils.ImageCodeUtils;
import com.tao.security.core.validate.image.ImageCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SecurityController
 * @Descriiption 测试Controller
 * @Author yanjiantao
 * @Date 2019/7/8 10:18
 **/
@Controller
public class SecurityController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @ResponseBody
    @GetMapping("/user/{id}")
    public Object user(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        return result;

    }

    @GetMapping("/")
    public String toIndex() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/user/signIn")
    public String signId() {
        return "signIn";
    }

    @GetMapping("/user/me")
    @ResponseBody
    public Result me(@AuthenticationPrincipal UserDetails user) {
        return Result.successMsg(user);
    }

    @GetMapping("/user/login/code/image")
    @ResponseBody
    public void codeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        ImageCode imageCode = ImageCodeUtils.generate(webRequest);
        sessionStrategy.setAttribute(webRequest, SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
