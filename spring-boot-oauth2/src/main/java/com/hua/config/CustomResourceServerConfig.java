/**
  * @filename CustomResourceServerConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

 /**
 * @type CustomResourceServerConfig
 * @description 
 * @author qianye.zheng
 */
@Configuration
@EnableResourceServer
public class CustomResourceServerConfig extends ResourceServerConfigurerAdapter
{
	//@Resource
	//private TokenStore tokenStore;
	
	/**
	 * @description 
	 * @param resources
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources)
			throws Exception
	{
		super.configure(resources);
		//resources.tokenStore(tokenStore).resourceId("resourceId");
	}
	
	/**
	 * @description 
	 * @param http
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		super.configure(http); 
		http.httpBasic().and().authorizeRequests().antMatchers("/home")
		.permitAll()
		.and().authorizeRequests().antMatchers("/user").access("#oauth2.hasScope('read')")
		.and().authorizeRequests().anyRequest().authenticated();
	}
	
	
}
