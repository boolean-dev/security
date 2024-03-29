package com.tao.security.core.properties;

import com.tao.security.core.validate.ValidateCode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SecurityProperties
 * @Descriiption spring security配置
 * @Author yanjiantao
 * @Date 2019/7/10 16:32
 **/
@Data
@ConfigurationProperties(prefix = "com.tao.security")
public class SecurityProperties {
    BrowserProperties browser = new BrowserProperties();
    ValidateCodeProperties code = new ValidateCodeProperties();
}
