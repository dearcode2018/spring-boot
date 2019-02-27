/**
  * @filename SampleJob.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @type SampleJob
 * @description 
 * @author qianye.zheng
 */
//@Component
public class SampleJob extends QuartzJobBean
{

	/**
	 * @description 
	 * @param context
	 * @throws JobExecutionException
	 * @author qianye.zheng
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException
	{
	}

}
