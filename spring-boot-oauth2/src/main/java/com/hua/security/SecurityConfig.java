/**
  * @filename SecurityConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.security;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @type SecurityConfig
 * @description 
 * @author qianye.zheng
 */
/* 启用Web安全认证 */
@EnableWebSecurity
/* 表明是一个 OAuth2.x 客户端 */
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Resource
	private OAuth2ClientContext oauth2ClientContext;
	
	/**
	 * @description 
	 * @param http
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		super.configure(http);
		
		http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class).antMatcher("/**").authorizeRequests()
		// 都允许访问
		.antMatchers("/", "'/index", "/403", "/css/**", "/js/**", "/font/**").permitAll()
		.anyRequest()
		.authenticated().and().logout().logoutSuccessUrl("/").permitAll()
		//.and().csrf().csrfTokenRepository(CookieCr);
	}
	
	private Filter ssoFilter()
	{
		OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
		OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
		//OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github());
		githubFilter.setRestTemplate(githubTemplate);
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		githubFilter.setTokenServices(defaultTokenServices);
		
		return githubFilter;
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	@ConfigurationProperties("github.client")
	public AuthorizationCodeResourceDetails github()
	{
		return new AuthorizationCodeResourceDetails();
	}

	
}
