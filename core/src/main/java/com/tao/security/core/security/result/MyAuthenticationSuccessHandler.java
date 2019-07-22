package com.tao.security.core.security.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tao.security.core.properties.LoginResponseType;
import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAuthenticationSuccessHandler
 * @Descriiption 自定义登录成功处理
 * @Author yanjiantao
 * @Date 2019/7/10 17:43
 **/
@Slf4j
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        logger.info("登录成功...");
        Gson gson = new Gson();
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(gson.toJson(Result.successMsg(authentication)));
        }else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
