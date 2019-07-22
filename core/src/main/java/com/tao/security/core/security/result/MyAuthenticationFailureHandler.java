package com.tao.security.core.security.result;

import com.google.gson.Gson;
import com.tao.security.core.properties.BrowserProperties;
import com.tao.security.core.properties.LoginResponseType;
import com.tao.security.core.properties.SecurityProperties;
import com.tao.security.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAuthenticationFailureHandler
 * @Descriiption 自定义登录失败处理器
 * @Author yanjiantao
 * @Date 2019/7/10 17:26
 **/
@Slf4j
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败...\ne={}", exception);
        Gson gson = new Gson();
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(gson.toJson(Result.errorMsg()));
        }else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
