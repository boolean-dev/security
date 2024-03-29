package com.tao.security.core.validate.handle;

import com.tao.security.core.properties.SecurityConstants;

/**
 * @ClassName ValidateCodeType
 * @Descriiption 验证码类型type
 * @Author yanjiantao
 * @Date 2019/7/22 15:48
 **/
public enum  ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    public abstract String getParamNameOnValidate();
}
