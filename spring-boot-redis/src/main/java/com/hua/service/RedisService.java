/**
  * @filename RedisService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @type RedisService
 * @description 
 * @author qianye.zheng
 */
@Service
public class RedisService
{

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
}
