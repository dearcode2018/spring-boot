/**
  * @filename SessionConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @type SessionConfig
 * @description 
 * @author qianye.zheng
 */
@EnableCaching
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 36000)
public class SessionConfig {
    
    
    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        // RedisHttpSessionConfiguration
        // 让SpringSession 不再执行 config 命令
        return ConfigureRedisAction.NO_OP;
    }
    
    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("gua-cookie"); 
        serializer.setCookiePath("/"); 
        //serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
        
        return serializer;
    }
    
}
