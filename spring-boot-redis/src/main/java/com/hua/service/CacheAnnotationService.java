/**
  * @filename CacheAnnotationService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.hua.entity.User;

 /**
 * @type CacheAnnotationService
 * @description 
 * @author qianye.zheng
 */
@Service
public class CacheAnnotationService
{
	
	//private String KEY_PREFIX = "User:";
	
	@Resource(name = "firstRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource
    private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 
	 * 使用Spring CacheManager
	 * 优点: 简化设置和获取缓存
	 * 缺点: 无法单独设置失效时间
	 * 
	 * 注解的参数: 
	 * value: 缓存名称，相当于缓存KEY前缀
	 * key: 缓存的后缀，代表具体的值
	 * condition: 条件，#param.propertyName，例如 #user.username.length() > 5
	 * 
	 * @Cacheable: 优先从缓存中获取
	 * 
	 * @CachePut: 和@Cacheable不同的是在方法执行前不会检查缓存是否
	 * 已存在之前执行过的结果，每次都会执行该方法
	 * 
	 * 
	 * @CacheEvict: 清除缓存，默认是方法执行成功后清除缓存(beforeInvocation=false)
	 * 默认情况下，如果方法执行抛异常，则不会清除缓存.
	 */
	
	/**
	 * 
	 * @description 
	 * @CachePut 每次调用该方法都会将结果存入指定的缓存
	 * @param user
	 * @author qianye.zheng
	 */
	@CachePut(value = {"user:info"}, key = "#user.id")
	public User cachePut(final User user)
	{
		System.out.println("CacheAnnotationService.setCache()");
		
		return user;
	}
	
	/**
	 * 
	 * @description 
	 * @Cacheable 优先从缓存获取
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@Cacheable(value = {"user:info"}, key = "#id")
	public User cacheable(final String id)
	{
		System.out.println("CacheAnnotationService.get()");
		// 模拟从数据库中获取
		User user = new User();
		user.setId(id);
		user.setUsername("张三FromDB");
		
		return user;
	}

	/**
	 * 
	 * @description 
	 * @CacheEvict 调用该方法之后，直接清除缓存
	 * beforeInvocation 是否在调用该方法之前清除，默认false
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@CacheEvict(value = {"user:info"}, key = "#user.id", beforeInvocation = false)
	public void cacheEvict(final User user)
	{
		System.out.println("CacheAnnotationService.removeCache()");
		// 更新数据库
	}
	
}
