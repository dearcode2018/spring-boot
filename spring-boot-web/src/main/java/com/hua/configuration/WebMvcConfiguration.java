package com.hua.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @type WebMvcConfiguration
 * @description MVC配置
 * @author qianye.zheng
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	
	
	/**
	 * @description 拦截器声明
	 * @param registry
	 * @author qianye.zheng
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		// 5M
		resolver.setMaxUploadSize(5000000);
		
		return resolver;
	}
	
	/**
	 * 
	 * @description 
	 * @param registry
	 * @author qianye.zheng
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	/*  */
/*    	registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
        registry.addResourceHandler("/dist/**").addResourceLocations("/dist/");       
        registry.addResourceHandler("/newStore/**").addResourceLocations("/newStore/");       
        registry.addResourceHandler("/staffModel/**").addResourceLocations("/staffModel/");      
        registry.addResourceHandler("/weChat/**").addResourceLocations("/weChat/");*/
    	//registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static1/");       
    	
    }
    
}
