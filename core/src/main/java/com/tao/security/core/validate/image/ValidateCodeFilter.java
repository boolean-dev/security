package com.tao.security.core.validate.image;

import com.tao.security.core.exception.ValidateImageCodeException;
import com.tao.security.core.properties.SecurityProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.tao.security.core.controller.SecurityController.SESSION_KEY;

/**
 * @ClassName ValidateCodeFilter
 * @Descriiption 验证码拦截器
 * @Author yanjiantao
 * @Date 2019/7/11 18:07
 **/
@Data
public class ValidateCodeFilter extends OncePerRequestFilter {

    // 验证失败handler
    private AuthenticationFailureHandler authenticationFailureHandler;

    // session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证码拦截器
     * @param request   request
     * @param response  respon
     * @param filterChain   filter拦截
     * @throws ServletException ServletException
     * @throws IOException  IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (StringUtils.equals(securityProperties.getBrowser().getLoginPage(), request.getRequestURI()) &&
                StringUtils.equalsIgnoreCase("POST", request.getMethod())) {
            try {
                this.validate(new ServletWebRequest(request));
            } catch (ValidateImageCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 校验图形验证码
     * @param servletWebRequest request
     * @throws ServletRequestBindingException   exception
     */
    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {

        // 从session中得到imageCode
        ImageValidateCode imageCodeSession = (ImageValidateCode) sessionStrategy.getAttribute(servletWebRequest, SESSION_KEY);

        // 从request中得到验证码
        String imageCode = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isEmpty(imageCode)) {
            throw new ValidateImageCodeException("图形验证码异常");
        }

        if (LocalDateTime.now().isAfter(imageCodeSession.getExpireTime())) {
            sessionStrategy.removeAttribute(servletWebRequest, SESSION_KEY);
            throw new ValidateImageCodeException("图形验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(imageCodeSession.getCode(), imageCode)) {
            throw new ValidateImageCodeException("验证码异常");
        }

        sessionStrategy.removeAttribute(servletWebRequest, SESSION_KEY);
    }


}
