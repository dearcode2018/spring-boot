/**
  * @filename SubscribeListener.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @type SubscribeListener
 * @description 订阅监听器
 * @author qianye.zheng
 */
public class SubscribeListener implements MessageListener {

	/**
	 * @description 
	 * @param message
	 * @param pattern
	 * @author qianye.zheng
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.print("模式: " + new String(pattern));
		System.out.println("收到渠道: " + new String(message.getChannel()) + " 的消息: " + new String(message.getBody()));
	}

}
