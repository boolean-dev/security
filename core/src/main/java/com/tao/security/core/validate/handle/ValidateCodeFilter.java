package com.tao.security.core.validate.handle;

import com.tao.security.core.exception.ValidateCodeException;
import com.tao.security.core.properties.SecurityConstants;
import com.tao.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ValidateCodeFilter
 * @Descriiption 验证码拦截器
 * @Author yanjiantao
 * @Date 2019/7/22 15:37
 **/

@Slf4j
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 验证码失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    /**
     * 验证码配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeProcessorHandel validateCodeProcessorHandel;

    /**
     * 存放所需校验码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();


    /**
     * 初始化bean
     * 初始化bean时，将需要校验的url初始化bean的urlMap中
     * @throws ServletException 异常
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        this.addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        this.addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = this.getValidateCodeType(request);

        if (type != null) {
            try {
                logger.info("校验请求（{}）中的验证码，验证码类型为{}", request.getRequestURI(), type);
                validateCodeProcessorHandel.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
            }

        }

        // 放行
        filterChain.doFilter(request, response);
    }

    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotEmpty(urlString)) {
            String[] urls = StringUtils.split(",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    /**
     * 判断该url是否需要被验证
     * @param request   the request
     * @return  验证类型
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType type = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    type = urlMap.get(url);
                }
            }
        }
        return type;
    }
}
