/**
  * @filename SessionConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;

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
        serializer.setDomainName(".hua.com");
        serializer.setCookieMaxAge(-1);
        serializer.setUseBase64Encoding(false);
        //serializer.setUseSecureCookie(true);
        //serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
        
        return serializer;
    }
    
    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    /*
     * @Bean
     * public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
     * return (factory) -> factory
     * .addContextCustomizers((context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
     * }
     */
    
}
