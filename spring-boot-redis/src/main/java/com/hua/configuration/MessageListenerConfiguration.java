/**
  * @filename MessageListenerConfiguration.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.hua.listener.SubscribeListener;

/**
 * @type MessageListenerConfiguration
 * @description 消息监听器配置
 * @author qianye.zheng
 */
@Configuration
public class MessageListenerConfiguration {

	/**
	 * 
	 * redis发布消息和连接的库没有关系，发布/订阅是进程级通用的
	 * 不接收历史消息
	 * 监听器启动后，开始接收发布者同一个时刻发布的消息
	 * 
	 */
	
	/**
	 * 
	 * @description 
	 * @param factory
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	public RedisMessageListenerContainer listenerContainer(final RedisConnectionFactory factory ) {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		// 模式主题: PatternTopic
		/**
		 * 显式主题和模式主题，有重合的，会收到重复消息，在实际应用中应避免
		 */
		container.addMessageListener(new MessageListenerAdapter(new SubscribeListener()), new ChannelTopic("new1"));
		//container.addMessageListener(new MessageListenerAdapter(new SubscribeListener()), new PatternTopic("new*"));
		
		return container;
	}
	
}
