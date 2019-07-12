package com.tao.security.core.validate.sms;

import com.tao.security.core.exception.ValidateImageCodeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.tao.security.core.controller.SecurityController.SESSION_SMS_KEY;

/**
 * @ClassName SmsCodeFilter
 * @Descriiption TODO
 * @Author yanjiantao
 * @Date 2019/7/12 18:00
 **/
@Slf4j
@Data
public class SmsCodeFilter extends OncePerRequestFilter {

    // 验证失败handler
    private AuthenticationFailureHandler authenticationFailureHandler;

    // session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/authentication/phone", request.getRequestURI()) &&
                StringUtils.equalsIgnoreCase("POST", request.getMethod())) {
            log.info("手机号登录...");
            try {
                this.validate(new ServletWebRequest(request));
            } catch (ValidateImageCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }
    }

    /**
     * 校验图形验证码
     * @param servletWebRequest request
     * @throws ServletRequestBindingException   exception
     */
    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {

        // 从session中得到imageCode
        SmsCode smsCodeSession = (SmsCode) sessionStrategy.getAttribute(servletWebRequest, SESSION_SMS_KEY);

        // 从request中得到验证码
        String smsCode = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "smsCode");

        if (StringUtils.isEmpty(smsCode)) {
            throw new ValidateImageCodeException("手机验证码异常");
        }

        if (LocalDateTime.now().isAfter(smsCodeSession.getExpireTime())) {
            sessionStrategy.removeAttribute(servletWebRequest, SESSION_SMS_KEY);
            throw new ValidateImageCodeException("图形验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(smsCodeSession.getCode(), smsCode)) {
            throw new ValidateImageCodeException("验证码异常");
        }

        sessionStrategy.removeAttribute(servletWebRequest, SESSION_SMS_KEY);
    }
}
