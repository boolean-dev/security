package com.tao.security.core.validate.sms;

import com.tao.security.core.exception.ValidateImageCodeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
 * @Descriiption 验证码过滤器
 * @Author yanjiantao
 * @Date 2019/7/12 18:00
 **/
@Slf4j
public class SmsCodeFilter extends AbstractAuthenticationProcessingFilter {

    public SmsCodeFilter() {
        super(new AntPathRequestMatcher("/authentication/phone", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!StringUtils.equalsIgnoreCase("POST", request.getMethod())) {
            throw new ValidateImageCodeException("请求方法必须为POST");
        }

        String phone = ServletRequestUtils.getRequiredStringParameter(request, "phone");

        if (StringUtils.isEmpty(phone)) {
            throw new ValidateImageCodeException("电话号为空");
        }

        SmsCodeAuthenticationToken smsToken = new SmsCodeAuthenticationToken(phone);
        smsToken.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(smsToken);
    }
}
