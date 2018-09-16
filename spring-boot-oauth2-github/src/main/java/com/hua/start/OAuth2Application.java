package com.hua.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication(scanBasePackages = {"com.hua"})
public class OAuth2Application extends WebSecurityConfigurerAdapter {

	/**
	 * 
	 * @description 
	 * @param args
	 * @author qianye.zheng
	 */
	public static void main(String[] args) 
	{
		SpringApplication.run(OAuth2Application.class, args);
	}
	
}