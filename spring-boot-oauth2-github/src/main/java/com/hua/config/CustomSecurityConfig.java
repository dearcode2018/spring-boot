/**
  * @filename CustomSecurityConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

 /**
 * @type CustomSecurityConfig
 * @description 
 * @author qianye.zheng
 */
@Configuration
/* 开启OAuth2.x客户端 */
@EnableOAuth2Client
/* 开启Auth服务器 */
//@EnableAuthorizationServer
//@Order(6)
/* 继承 WebSecurityConfigurerAdapter  */
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Resource
	private OAuth2ClientContext oauth2ClientContext;
	
	/**
	 * 
	 * @description 
	 * @param filter
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
		registration.setFilter(filter);
		registration.setOrder(-100);
		
		return registration;
	}

	/**
	 * 
	 * @description github 配置
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	@ConfigurationProperties("github")
	public ClientResources github() {
		return new ClientResources();
	}

	/**
	 * 
	 * @description 
	 * @param http
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests()
		// 匹配url
		/**
		 * 一个斜杠: 首页
		 * login webjars error 不拦截的地址
		 */
		.antMatchers("/", "/login**", "/webjars/**", "/error**", "/callback**").permitAll().anyRequest()
		.authenticated().and().exceptionHandling()
		.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
		.logoutSuccessUrl("/logout.html").permitAll().and().csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
		.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}	
	
	/**
	 * 
	 * @description 拦截器
	 * @return
	 * @author qianye.zheng
	 */
	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(github(), "/login/github"));
		filter.setFilters(filters);
		
		return filter;
	}

	/**
	 * 
	 * @description 
	 * @param client
	 * @param path
	 * @return
	 * @author qianye.zheng
	 */
	private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter =
				new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate oAuth2RestTemplate = 
				new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
		// github.resource.userInfoUri, github.client.clientId
		UserInfoTokenServices tokenServices = 
				new UserInfoTokenServices(client.getResource().getUserInfoUri(),
				client.getClient().getClientId());
		tokenServices.setRestTemplate(oAuth2RestTemplate);
		// 发送请求获取令牌
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		
		return oAuth2ClientAuthenticationFilter;
	}	
	
	/**
	 * 
	 * @type ResourceServerConfiguration
	 * @description 
	 * @author qianye.zheng
	 */
/*	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.antMatcher("/me").authorizeRequests().anyRequest().authenticated();
			// @formatter:on
		}
	}	*/
	
	/**
	 * 
	 * @type ClientResources 配置资源
	 * @description 
	 * @author qianye.zheng
	 */
	class ClientResources
	{
		@NestedConfigurationProperty
		private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

		@NestedConfigurationProperty
		private ResourceServerProperties resource = new ResourceServerProperties();

		public AuthorizationCodeResourceDetails getClient()
		{
			return client;
		}

		public ResourceServerProperties getResource()
		{
			return resource;
		}
	}
	
}
