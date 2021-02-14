/**
  * @filename CustomExitCodeGenerator.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.run;

import org.springframework.boot.ExitCodeGenerator;

/**
 * @type CustomExitCodeGenerator
 * @description 
 * @author qianye.zheng
 */
public class CustomExitCodeGenerator implements ExitCodeGenerator
{

	
	/**
	 * 指定 退出代码
	 */
	
	/**
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public int getExitCode()
	{
		return 0;
	}

}
