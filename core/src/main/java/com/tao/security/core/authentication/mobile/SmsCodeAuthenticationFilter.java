package com.tao.security.core.authentication.mobile;

import com.tao.security.core.properties.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @ClassName SmsCodeAuthenticationFilter
 * @Descriiption 手机验证码认证器
 * @Author yanjiantao
 * @Date 2019/7/22 15:02
 **/
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String MOBILE_PARAMETER = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;


    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!StringUtils.equalsIgnoreCase("post", request.getMethod())){
            throw new AuthenticationServiceException("登录请求只支持POST方法");
        }

        String mobile = this.getMobile(request);

        if(StringUtils.isBlank(mobile)){
            throw new AuthenticationServiceException("手机号不能为空");
        }



        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        this.setDetails(request, authRequest);

        return super.getAuthenticationManager().authenticate(authRequest);
    }

    private String getMobile(HttpServletRequest request) throws ServletRequestBindingException {
        return ServletRequestUtils.getStringParameter(request, MOBILE_PARAMETER);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
