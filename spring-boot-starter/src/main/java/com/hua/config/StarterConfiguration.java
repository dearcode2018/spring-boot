/**
  * @filename StarterConfiguration.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hua.service.StarterService;

/**
 * @type StarterConfiguration
 * @description 
 * @author qianye.zheng
 */
@Configuration
@EnableConfigurationProperties(StarterConfigProperties.class)
// 配置初始化条件
@ConditionalOnProperty(prefix = "starter.config", name = "open", havingValue = "true")
public class StarterConfiguration {

    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    @Bean
    public StarterService build() {
        return new StarterService();
    }
    
}
