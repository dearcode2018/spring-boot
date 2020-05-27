/**
  * @filename ScheduledTask.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

/**
 * @type ScheduledTask
 * @description 
 * @author qianye.zheng
 */
/*
 * 通过@ConditionalOnProperty控制任务是否开启
 */
@ConditionalOnProperty(name = "schedule.enable", havingValue = "true")
@Component
@EnableScheduling
// 开启线程池调度任务的方式，对应@Async
//@EnableAsync
public class ScheduledTask
{

	
	/**
	 * fixedRate: 两次调用之间 固定频率, 一次调用开始多久后开始下一次调用
	 * 
	 * initialDelay: 初始化延迟
	 * 
	 * fixedDelay: 本次调用结束和下一次调用开始的间隔时间，即一次调用结束后延迟多久开始下一次调用
	 */
	
	/**
	 * 
	 * @description 
	 * fixedRate 固定频率，指定间隔时间，单位毫秒
	 * @author qianye.zheng
	 */
	//@Async
	@Scheduled(fixedRate = 1000 * 10)
	public void task1()
	{
		System.out.println("ScheduledTask.task1()");
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	//@Async
	@Schedules({@Scheduled(initialDelay = 1 * 1000, fixedDelay = 5 * 1000),
		@Scheduled(cron = "50 * * * * ?")	
	})
	//@Scheduled(cron = "50 * * * * ?")	
	public void task2()
	{
		System.out.println("ScheduledTask.task2()");
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	@Scheduled(cron = "* 20 * * * ?")
	public void task3()
	{
		System.out.println("ScheduledTask.task3()");
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	@Schedules({@Scheduled(initialDelay = 10 * 1000, fixedDelay = 5 * 1000)
	})
	//@Scheduled(cron = "50 * * * * ?")	
	public void task4()
	{
		System.out.println("ScheduledTask.task4()");
	}
}
