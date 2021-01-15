/**
  * @filename WebSecurityConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hua.interceptor.RedisSessionInterceptor;

/**
 * @type WebSecurityConfig
 * @description 
 * @author qianye.zheng
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer
{

	
	/**
	 * @description 
	 * @param registry
	 * @author qianye.zheng
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    //所有已api开头的访问都要进入RedisSessionInterceptor拦截器进行登录验证，并排除login接口(全路径)。必须写成链式，分别设置的话会创建多个拦截器。
        //必须写成getSessionInterceptor()，否则SessionInterceptor中的@Autowired会无效
        registry.addInterceptor(getSessionInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login");
	}
	
	/**
     * @description 
     * @param converters
     * @author qianye.zheng
     */
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Iterator<HttpMessageConverter<?>> it = converters.iterator();
        while (it.hasNext()) {
            HttpMessageConverter<?> e = it.next();
            /*
             * // 顺带设置了编码 if (e instanceof StringHttpMessageConverter) {
             * ((StringHttpMessageConverter)
             * e).setDefaultCharset(StandardCharsets.UTF_8); } // 在客户端没有设置
             * "Accept", "application/json"，服务端设置UTF-8编码解决中文乱码问题 if (e
             * instanceof MappingJackson2HttpMessageConverter) {
             * ((MappingJackson2HttpMessageConverter)
             * e).setDefaultCharset(StandardCharsets.UTF_8); }
             */
            // 将所有转换器的默认编码设置为 UTF-8
            if (e instanceof AbstractHttpMessageConverter) {
                ((AbstractHttpMessageConverter<?>) e).setDefaultCharset(StandardCharsets.UTF_8); 
            }
            if (e instanceof FormHttpMessageConverter) {
                ((FormHttpMessageConverter) e).setCharset(StandardCharsets.UTF_8); 
            }
        }
    }
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	public RedisSessionInterceptor getSessionInterceptor()
	{
		return new RedisSessionInterceptor();
	}
	
}
