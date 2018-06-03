/**
  * @filename CacheAnnotationService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import javax.annotation.Resource;

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
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource
    private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 
	 * @description 
	 * @param user
	 * @author qianye.zheng
	 */
	@CachePut(key = "'User:' +#user.id", value = {"user"})
	public void add(final User user)
	{
		
	}
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @param name
	 * @return
	 * @author qianye.zheng
	 */
	@Cacheable(key = "'User':+#id")
	public User add(final String id, final String name)
	{
		User user = new User();
		user.setId(id);
		user.setUsername(name);
		
		return user;
	}

}
