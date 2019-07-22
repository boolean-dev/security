package com.tao.security.core.authentication.mobile;

import com.tao.security.core.properties.SecurityConstants;
import com.tao.security.core.validate.sms.SmsValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.tao.security.core.controller.SecurityController.SESSION_SMS_KEY;

/**
 * @ClassName SmsCodeAuthenticationFilter
 * @Descriiption 手机验证码认证器
 * @Author yanjiantao
 * @Date 2019/7/22 15:02
 **/
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!StringUtils.equalsIgnoreCase("post", request.getMethod())){
            throw new AuthenticationServiceException("登录请求只支持POST方法");
        }

        String phone = ServletRequestUtils.getStringParameter(request, "phone");

        if(StringUtils.isBlank(phone)){
            throw new AuthenticationServiceException("手机号不能为空");
        }

        String code = ServletRequestUtils.getStringParameter(request, "smsCode");
        SmsValidateCode smsCode = (SmsValidateCode) sessionStrategy.getAttribute(new ServletWebRequest(request), SESSION_SMS_KEY);

        if (smsCode == null) {
            throw new AuthenticationServiceException("验证码不存在");
        }

        if (LocalDateTime.now().isAfter(smsCode.getExpireTime())) {
            throw new AuthenticationServiceException("验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(code, smsCode.getCode())) {
            throw new AuthenticationServiceException("验证码错误");
        }

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(phone);

        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return getAuthenticationManager().authenticate(authRequest);
    }
}
