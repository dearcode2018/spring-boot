package com.hua.configure;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.unit.DataSize;
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

	@Value("${web.resource.static:0}")
	private String[] staticResource;
	
	/* 请求体最大值，单位: K */
	@Value("${data.request.maxSizeK:5000}")
	private Integer maxRequestSizeK;
	
	/* 上传文件最大值，单位: K */
	@Value("${data.fileUpload.maxSizeK:5000}")
	private Integer maxUploadFileSizeK;
	
	/**
	 * 
	 * spring.servlet.multipart.max-file-size = 100MB
	 * spring.servlet.multipart.max-request-size = 150MB
	 */
	
	/**
	 * 
	 * @description 文件上传配置
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		final MultipartConfigFactory factory = new MultipartConfigFactory();
		// 请求体最大值
		factory.setMaxRequestSize(DataSize.ofKilobytes(maxRequestSizeK));
		// 上传文件最大值
		factory.setMaxFileSize(DataSize.ofKilobytes(maxUploadFileSizeK));
		
		return factory.createMultipartConfig();
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
/*	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		// 5M
		resolver.setMaxUploadSize(5000000);
		
		return resolver;
	}*/
	
	
	/**
	 * 
	 * @description 
	 * @param registry
	 * @author qianye.zheng
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	/* swagger */
        registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
	@Bean
	public Converter<String, LocalDate> localDateConverter() {
		return new Converter<String, LocalDate>() {
			@Override
			public LocalDate convert(String s) {
				return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}
		};
	}
	
}
