package com.tao.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SecurityProperties
 * @Descriiption spring security配置
 * @Author yanjiantao
 * @Date 2019/7/10 16:32
 **/
@Data
@ConfigurationProperties(prefix = "my.security")
public class SecurityProperties {
    BrowserProperties browser = new BrowserProperties();
}
