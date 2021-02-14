/**
 * 描述: 
 * PersonControllerTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.controller;

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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.hua.ApplicationStarter;
import com.hua.bean.PersonSearchBean;
import com.hua.bean.ResultBean;
import com.hua.test.BaseTest;
import com.hua.util.JacksonUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * PersonControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class}, 
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@MapperScan(basePackages = {"com.hua.mapper"})
public class PersonControllerTest extends BaseTest {

    /* @LocalServerPort 提供了 @Value("${local.server.port}") 的代替 */
   @LocalServerPort
   protected int port;
   
   protected String prefix;
   
   protected String url;
   
   /* org.springframework.boot.test.web.client.TestRestTemplate */
   @Resource
   private TestRestTemplate testRestTemplate;
	
	//@Resource
	//private PersonDao personDao;
	
   /**
    * 
    * @description 
    * @author qianye.zheng
    */
   @Before
   public void beforeMethod()
   {
	   prefix = "/person";
	   System.out.println("port: " + port);
   }
   
    
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testPostInBody() {
		try {
			url = prefix + "/postNotInBody";
			PersonSearchBean searchBean = new PersonSearchBean();
			searchBean.setName("zhangsan");
			searchBean.setPassword("1234567");
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", "application/json;charset=UTF-8");
			headers.add("Accept", "application/json");
			
			HttpEntity<String> httpEntity = new HttpEntity<String>(JacksonUtil.writeAsString(searchBean), headers);
			
			ResponseEntity<ResultBean> responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, httpEntity, ResultBean.class);
			
			System.out.println(JacksonUtil.writeAsString(responseEntity.getBody()));
			
		} catch (Exception e) {
			log.error("testPostInBody =====> ", e);
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
