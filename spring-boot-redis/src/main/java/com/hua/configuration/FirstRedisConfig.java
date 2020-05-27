/**
  * @filename FirstRedisConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import java.net.UnknownHostException;
import java.time.Duration;

import javax.annotation.Resource;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @type FirstRedisConfig
 * @description
 * @author qianye.zheng
 */
@Configuration
public class FirstRedisConfig
{
	@Resource
	private FirstRedisProperties firstRedisProperties;

	/**
	 * 
	 * @description
	 * @param redisConnectionFactory
	 * @return
	 * @throws UnknownHostException
	 * @author qianye.zheng
	 */
	@Primary
	@Bean("firstRedisTemplate")
	public StringRedisTemplate stringRedisTemplate()
	{
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(connectionFactory());

		return template;
	}

	/**
	 * 
	 * @description
	 * @return
	 * @author qianye.zheng
	 */
	//@Primary
	@Bean
	public RedisConnectionFactory connectionFactory()
	{
		final RedisStandaloneConfiguration re = new RedisStandaloneConfiguration();
		re.setHostName(firstRedisProperties.getHost());
		re.setPassword(firstRedisProperties.getPassword());
		re.setDatabase(firstRedisProperties.getDatabase());
		re.setPort(firstRedisProperties.getPort());
		//final JedisClientConfiguration.JedisClientConfigurationBuilder  builder = JedisClientConfiguration.builder();
		//final JedisPoolConfig poolConfig = new JedisPoolConfig();
		//poolConfig.setMinIdle(firstRedisProperties.getMinIdle());
		//poolConfig.setMaxIdle(firstRedisProperties.getMaxIdle());
		//poolConfig.setMaxTotal(firstRedisProperties.getMaxTotal());
		//poolConfig.setMaxWaitMillis(firstRedisProperties.getMaxWaitMillis());
		//poolConfig.setTestOnCreate(true);
		//builder.usePooling().poolConfig(poolConfig);
		//builder.readTimeout(Duration.ofMillis(firstRedisProperties.getReadTimeout())).connectTimeout(Duration.ofMillis(firstRedisProperties.getConnectTimeout()));
		
		return new JedisConnectionFactory(re);
	}
	
	/**
	 * 
	 * @description 缓存管理器
	 * Spring Cache Annotation
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	public CacheManager cacheManager()
	{
		//final RedisCacheManagerBuilder builder = RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory());
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()
				// 设置统一的失效时间
				.entryTtl(Duration.ofMinutes(10))
				// 静态前缀，固定的前缀，每个缓存都带有
				//.prefixKeysWith("")
				// 计算前缀，动态计算的，即分隔符
				.computePrefixWith(cacheName ->  cacheName + ":")
				/* 实体不能声明为final，否则发序列化会出现异常 */
				// VALUE序列化
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
				//.serializeValuesWith(SerializationPair.fromSerializer(StringRedisSerializer.UTF_8));
		//configuration = configuration.prefixKeysWith(":");
		// 序列化 KEY
		//configuration.serializeKeysWith(new StringRedisSerializer());
		// 序列化 VALUE
		//configuration.serializeValuesWith();
		
		//builder.cacheDefaults(configuration);
		final RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory()).cacheDefaults(configuration)
				.transactionAware().build();
		//final RedisCacheManager cacheManager = builder.build();
		//cacheManager.afterPropertiesSet();
		
		return cacheManager;
	}

}
