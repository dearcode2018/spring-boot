/**
  * @filename WebSecurityConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.hua.interceptor.RedisSessionInterceptor;

/**
 * @type WebSecurityConfig
 * @description 
 * @author qianye.zheng
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurationSupport
{

	
	/**
	 * @description 
	 * @param registry
	 * @author qianye.zheng
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry)
	{
	    //所有已api开头的访问都要进入RedisSessionInterceptor拦截器进行登录验证，并排除login接口(全路径)。必须写成链式，分别设置的话会创建多个拦截器。
        //必须写成getSessionInterceptor()，否则SessionInterceptor中的@Autowired会无效
        registry.addInterceptor(getSessionInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/user/login");
        super.addInterceptors(registry);
	}
	
	@Bean
	public RedisSessionInterceptor getSessionInterceptor()
	{
		return new RedisSessionInterceptor();
	}
	
}
