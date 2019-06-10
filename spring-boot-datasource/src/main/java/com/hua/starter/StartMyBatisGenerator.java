/**
  * @filename StartMyBatisGenerator.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.starter;

import org.mybatis.generator.api.ShellRunner;

import com.hua.util.ClassPathUtil;

/**
 * @type StartMyBatisGenerator
 * @description 
 * @author qianye.zheng
 */
public final class StartMyBatisGenerator
{

	/**
	 * @description 
	 * @param args
	 * @author qianye.zheng
	 */
	public static void main(String[] args)
	{
		/**
		 * -verbose: 输出进度
		 */
		String[] params = {"-configfile", ClassPathUtil.getClassSubpath("/mybatis-generator.xml"), 
				"-overwrite", "-verbose"};
		ShellRunner.main(params);
	}

}
