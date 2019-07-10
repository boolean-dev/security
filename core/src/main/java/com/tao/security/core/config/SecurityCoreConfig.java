package com.tao.security.core.config;

import com.tao.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SecurityCoreConfig
 * @Descriiption 自定义的spring security配置
 * @Author yanjiantao
 * @Date 2019/7/10 16:38
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
