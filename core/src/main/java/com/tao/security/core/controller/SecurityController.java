package com.tao.security.core.controller;

import com.tao.security.core.properties.SecurityConstants;
import com.tao.security.core.result.Result;
import com.tao.security.core.validate.handle.ValidateCodeProcessorHandel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName SecurityController
 * @Descriiption 测试Controller
 * @Author yanjiantao
 * @Date 2019/7/8 10:18
 **/
@Slf4j
@Controller
public class SecurityController {

    @Autowired
    private ValidateCodeProcessorHandel validateCodeProcessorHandel;

    @GetMapping("/")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @GetMapping("/authentication/require")
    public String toLogin() {
        return "unAuthentication";
    }

    @GetMapping("/authentication/signIn")
    public String signId() {
        return "signIn";
    }

    @GetMapping("/user/me")
    @ResponseBody
    public Result me(@AuthenticationPrincipal UserDetails user) {
        return Result.successMsg(user);
    }

    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorHandel.findValidateCodeProcessor(type).create(new ServletWebRequest(request,response));
    }

}
