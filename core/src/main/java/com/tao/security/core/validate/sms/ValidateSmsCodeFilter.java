package com.tao.security.core.validate.sms;

import com.tao.security.core.exception.ValidateImageCodeException;
import com.tao.security.core.validate.image.ImageCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
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

import static com.tao.security.core.controller.SecurityController.SESSION_KEY;
import static com.tao.security.core.controller.SecurityController.SESSION_SMS_KEY;

/**
 * @ClassName ValidateSmsCodeFilter
 * @Descriiption 手机验证码拦截器
 * @Author yanjiantao
 * @Date 2019/7/12 16:39
 **/
@Slf4j
public class ValidateSmsCodeFilter extends AbstractAuthenticationProcessingFilter {

    // 验证失败handler
    private AuthenticationFailureHandler authenticationFailureHandler;

    // session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public ValidateSmsCodeFilter() {
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    protected ValidateSmsCodeFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected ValidateSmsCodeFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!StringUtils.equalsIgnoreCase("post", request.getMethod())){
            throw new AuthenticationServiceException("登录请求只支持POST方法");
        }

        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");

        if(StringUtils.isBlank(mobile)){
            throw new AuthenticationServiceException("手机号不能为空");
        }

        String phone = ServletRequestUtils.getStringParameter(request, "smsCode");

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(phone);

        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return getAuthenticationManager().authenticate(authRequest);
    }


}
