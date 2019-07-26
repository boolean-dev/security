package com.tao.security.core.validate.impl;

import com.tao.security.core.exception.ValidateCodeException;
import com.tao.security.core.validate.ValidateCode;
import com.tao.security.core.validate.handle.ValidateCodeGenerator;
import com.tao.security.core.validate.handle.ValidateCodeProcessor;
import com.tao.security.core.validate.handle.ValidateCodeType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName AbstractValidateCodeProcessor
 * @Descriiption 默认的验证码处理器
 * @Author yanjiantao
 * @Date 2019/7/22 16:39
 **/
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作session的工具类-spring提供的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中的验证码生成器的实现类{@link ValidateCodeGenerator}
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = this.generate(request);
        this.save(request, validateCode);
        this.send(request, validateCode);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);


        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

    /**
     * 保存验证码至session中
     * @param validateCode  验证码
     */
    private void save(ServletWebRequest request, ValidateCode validateCode) {
        sessionStrategy.setAttribute(request, this.getSessionKey(request), validateCode);
    }

    /**
     * 发送验证码
     * @param request   请求
     * @param validateCode  验证码
     * @throws Exception    异常
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        ValidateCodeType type = this.getValidateCodeType(request);
        String generateName = type.name().toLowerCase() + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generateName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generateName + "不存在");
        }

        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 根据子类的类型，得到需验证的类型
     * @param request   the request
     * @return  the ValidateCodeType
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request the request
     * @return  the key of session
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }
}
