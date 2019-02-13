/**
 * 描述: 
 * SpringBootConfigTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.config;

// 静态导入
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.hua.ApplicationStarter;
import com.hua.config.UserConfig;
import com.hua.config.UserConfig2;
import com.hua.test.BaseTest;
import com.hua.util.JacksonUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * SpringBootConfigTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
//@MapperScan(basePackages = {"com.hua.mapper"})
public class SpringBootConfigTest extends BaseTest {

	//@Resource
	//private PersonDao personDao;
	
	@Value("${server.contextPath}")
	private String serverContextPath;
	
	@Value("${server.contextPathX}")
	private String serverContextPathX;
	
	@Resource
	private Environment envrionment;
	
	/* 通过 spring config配置引入的自定义配置 */
	//@Value("${user.name}")
	//private String username;
	
	@Resource
	private UserConfig userConfig;
	
	@Resource
	private UserConfig2 userConfig2;
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testValue() {
		try {
			log.info("testValue =====> serverContextPath = " + serverContextPath);
			log.info("testValue =====> serverContextPathX = " + serverContextPathX);
		} catch (Exception e) {
			log.error("testValue =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testEnvironment() {
		try {
			/*
			 * 环境包括: 系统配置 + 核心配置
			 */
			// 系统配置 Administrator
			log.info("testEnvironment =====> " + envrionment.getProperty("user.name"));
			
			// 自定义配置: 无法获取
			log.info("testEnvironment =====> " + envrionment.getProperty("user.age"));
			
			// 核心配置
			log.info("testEnvironment =====> " + envrionment.getProperty("server.contextPath"));
		} catch (Exception e) {
			log.error("testEnvironment =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testConfigurationProperties() {
		try {
			
			//log.info("testConfigurationProperties =====> username = " + username);
			/**
			 * user.name 被系统的属性所覆盖，因此在定义key的时候注意
			 * 不要跟系统与核心的重复，否则配置无法生效.
			 * {"name":"Administrator","password":"123456","age":34,
			 * "address":"GuangZhouCity","remark":null}
			 */
			// 取自核心配置文件 application.properties
			System.out.println(JacksonUtil.writeAsString(userConfig));
			
			// 自定义配置 取自 project.properties
			System.out.println(userConfig2.getName());
			System.out.println(userConfig2.getPassword());
			System.out.println(userConfig2.getAge());
		} catch (Exception e) {
			log.error("testConfigurationProperties =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void test() {
		try {
			
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testTemp() {
		try {
			
			
		} catch (Exception e) {
			log.error("testTemp=====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testCommon() {
		try {
			
			
		} catch (Exception e) {
			log.error("testCommon =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testSimple() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSimple =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@Ignore("解决ide静态导入消除问题 ")
	private void noUse() {
		String expected = null;
		String actual = null;
		Object[] expecteds = null;
		Object[] actuals = null;
		String message = null;
		
		assertEquals(expected, actual);
		assertEquals(message, expected, actual);
		assertNotEquals(expected, actual);
		assertNotEquals(message, expected, actual);
		
		assertArrayEquals(expecteds, actuals);
		assertArrayEquals(message, expecteds, actuals);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(message, true);
		assertTrue(message, true);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(message, expecteds, actuals);
		assertNotSame(message, expecteds, actuals);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(message, actuals);
		assertNotNull(message, actuals);
		
		assertThat(null, null);
		assertThat(null, null, null);
		
		fail();
		fail("Not yet implemented");
		
	}

}
