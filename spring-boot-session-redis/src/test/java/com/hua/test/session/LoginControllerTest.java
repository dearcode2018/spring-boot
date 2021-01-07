/**
 * 描述: 
 * LoginControllerTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.session;

//静态导入
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.session.Session;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hua.constant.CacheKeys;
import com.hua.test.common.BaseControllerTest;
import com.hua.util.ClassPathUtil;
import com.hua.util.ProjectUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * LoginControllerTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
// for Junit 5.x
//@SpringBootTest(classes = {ApplicationStarter.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableRedisHttpSession
public final class LoginControllerTest extends BaseControllerTest {

	
	/*
	配置方式1: 
	@WebAppConfiguration(value = "src/main/webapp")  
	@ContextConfiguration(locations = {
			"classpath:conf/xml/spring-bean.xml", 
			"classpath:conf/xml/spring-config.xml", 
			"classpath:conf/xml/spring-mvc.xml", 
			"classpath:conf/xml/spring-service.xml"
		})
	@ExtendWith(SpringExtension.class)
	
	配置方式2: 	
	@WebAppConfiguration(value = "src/main/webapp")  
	@ContextHierarchy({  
		 @ContextConfiguration(name = "parent", locations = "classpath:spring-config.xml"),  
		 @ContextConfiguration(name = "child", locations = "classpath:spring-mvc.xml")  
		}) 
	@ExtendWith(SpringExtension.class)
	 */
	
	/**
	 * 而启动spring 及其mvc环境，然后通过注入方式，可以走完 spring mvc 完整的流程.
	 * 
	 */

	
	//@PathVariable
	
	//@Resource(type = WebApplicationContext.class)
	//@Autowired
	@Resource
    private WebApplicationContext webApplicationContext; 
	
	/**
	 * 引当前项目用其他项目之后，然后可以使用
	 * SpringJunitTest模板测试的其他项目
	 * 
	 * 可以使用所引用目标项目的所有资源
	 * 若引用的项目的配置与本地的冲突或无法生效，需要
	 * 将目标项目的配置复制到当前项目同一路径下
	 * 
	 */
	
	/**
     * 
     * 描述: 
     * @author qye.zheng
     * 
     */
    @Test
    public void testLogin() {
        try {
            // 页面/服务 地址
            String url = prefix + "/login";
            // 请求构建器
            // get 方法
            //MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
            // post 方法
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
            requestBuilder.header("Content-Type", "application/json;charset=UTF-8");
            requestBuilder.header("Accept", "application/json");
            
            //String sessionId = "8cc99da7-4c7a-4997-b0a8-d91ce50dce45";
            //MockHttpSession session = new MockHttpSession(webApplicationContext.getServletContext(), sessionId);
            // 请求对象关联会话对象 - 构造一个自定义的MockHttpSession对象
            MockHttpSession session = new MockHttpSessionWrapper();
            requestBuilder.session(session);
            
            /*
             * 设置请求参数
             */
            requestBuilder.param("username", "admin");
            
            // 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
            MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
            // mvc 结果
            MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
            // 请求body
            //requestBuilder.content("{}");     
            
            // 响应对象
            MockHttpServletResponse response = mvcResult.getResponse();
            // 获取字符串形式的响应内容
            String result = response.getContentAsString();
            
            System.out.println(result);
            
            // 异常对象
            //Exception exception = mvcResult.getResolvedException();
            
        } catch (Exception e) {
            log.error("testMockMVC =====> ", e);
        }
    }
	
    /**
     * 
     * 描述: 
     * @author qye.zheng
     * 
     */
    @Test
    public void testGet() {
        try {
            // 页面/服务 地址
            String url = prefix + "/get";
            // 请求构建器
            // get 方法
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
            // post 方法
            //MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
            requestBuilder.header("Content-Type", "application/json;charset=UTF-8");
            requestBuilder.header("Accept", "application/json");
                      
            /*
             * 构造session，设置相关信息，跟缓存建立关联
             * sessionId 在登录后，从服务端日志中获取，可以设置一个较长时间的值
             */
            String sessionId = "fa89c960-a460-491f-8821-5dc1d6fa5a54";
            //MockHttpSession session = new MockHttpSession(webApplicationContext.getServletContext(), sessionId);
            // 请求对象关联会话对象 - 构造一个自定义的MockHttpSession对象
            MockHttpSession session = new MockHttpSessionWrapper(sessionId);
            requestBuilder.session(session);
            
            /*
             * 设置请求参数
             */
            
            // 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
            MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
            // mvc 结果
            MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
            // 请求body
            //requestBuilder.content("{}");     
            
            // 响应对象
            MockHttpServletResponse response = mvcResult.getResponse();
            // 获取字符串形式的响应内容
            String result = response.getContentAsString();
            
            System.out.println(result);
            
            // 异常对象
            //Exception exception = mvcResult.getResolvedException();
            
            
        } catch (Exception e) {
            log.error("testMockMVC =====> ", e);
        }
    }
    
    /**
     * 
     * 描述: 
     * @author qye.zheng
     * 
     */
    @Test
    public void testGetExperiment() {
        try {
            // 页面/服务 地址
            String url = prefix + "/get";
            // 请求构建器
            // get 方法
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
            // post 方法
            //MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
            requestBuilder.header("Content-Type", "application/json;charset=UTF-8");
            requestBuilder.header("Accept", "application/json");
            /*
             * 通过传cookie的方式不行，因为缺少session对象，无法从redis中读取信息
             * 单独使用 MockHttpSession 以及 MockHttpSession和 Cookie结合的方式
             * 均无法走通后面的流程，可以看出，应该是注入 RedisSession 才可以
             */
            //Cookie cookie = new Cookie("SESSION", "OGNjOTlkYTctNGM3YS00OTk3LWIwYTgtZDkxY2U1MGRjZTQ1");
            //Cookie cookie = new Cookie("gua-cookie", "OGNjOTlkYTctNGM3YS00OTk3LWIwYTgtZDkxY2U1MGRjZTQ1");
            //requestBuilder.cookie(cookie);
            
            /*
             * 构造session，设置相关信息，跟缓存建立关联
             */
            String sessionId = "8cc99da7-4c7a-4997-b0a8-d91ce50dce45";
            //MockHttpSession session = new MockHttpSession(webApplicationContext.getServletContext(), sessionId);
            // 请求对象关联会话对象
            MockHttpSession session = new MockHttpSessionWrapper(sessionId);
            requestBuilder.session(session);
            
            // 
            //requestBuilder.header("Cookie", "SESSION=NTQ1ZTIzMTAtZmE0ZS00ODE1LWIxMWQtN2NmYmZkZTE3NTY1");
            /*
             * 设置请求参数
             */
            
            // 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
            MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
            // mvc 结果
            MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
            // 请求body
            //requestBuilder.content("{}");     
            
            // 响应对象
            MockHttpServletResponse response = mvcResult.getResponse();
            // 获取字符串形式的响应内容
            String result = response.getContentAsString();
            
            System.out.println(result);
            
            // 异常对象
            //Exception exception = mvcResult.getResolvedException();
            
            
        } catch (Exception e) {
            log.error("testMockMVC =====> ", e);
        }
    }

    /**
  * 
  * 描述: 
  * @author qye.zheng
  * 
  */
 //@DisplayName("test")
 @Test
 public void testGetSession() {
     try {
         String sessionId = "8cc99da7-4c7a-4997-b0a8-d91ce50dce45";
         Session session = redisIndexedSessionRepository.findById(sessionId);
         Object value = session.getAttribute(CacheKeys.Login.USER_ID.getCacheKey());
         System.out.println(value);
         
         MockHttpSessionWrapper wrapper = new MockHttpSessionWrapper(sessionId);
         Object value2 = wrapper.getAttribute(CacheKeys.Login.USER_ID.getCacheKey());
         System.out.println(value2);
         
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
    public void testGetOld() {
        try {
            // 页面/服务 地址
            String url = prefix + "/get";
            // 请求构建器
            // get 方法
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
            // post 方法
            //MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
            requestBuilder.header("Content-Type", "application/json;charset=UTF-8");
            requestBuilder.header("Accept", "application/json");
            /*
             * 通过传cookie的方式不行，因为缺少session对象，无法从redis中读取信息
             * 单独使用 MockHttpSession 以及 MockHttpSession和 Cookie结合的方式
             * 均无法走通后面的流程，可以看出，应该是注入 RedisSession 才可以
             */
            //Cookie cookie = new Cookie("SESSION", "OGNjOTlkYTctNGM3YS00OTk3LWIwYTgtZDkxY2U1MGRjZTQ1");
            Cookie cookie = new Cookie("gua-cookie", "OGNjOTlkYTctNGM3YS00OTk3LWIwYTgtZDkxY2U1MGRjZTQ1");
            requestBuilder.cookie(cookie);
            
            /*
             * 构造session，设置相关信息，跟缓存建立关联
             */
            String sessionId = "8cc99da7-4c7a-4997-b0a8-d91ce50dce45";
            MockHttpSession session = new MockHttpSession(webApplicationContext.getServletContext(), sessionId);
            // 请求对象关联会话对象
            requestBuilder.session(session);
            
            // 
            //requestBuilder.header("Cookie", "SESSION=NTQ1ZTIzMTAtZmE0ZS00ODE1LWIxMWQtN2NmYmZkZTE3NTY1");
            /*
             * 设置请求参数
             */
            
            // 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
            MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
            // mvc 结果
            MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
            // 请求body
            //requestBuilder.content("{}");     
            
            // 响应对象
            MockHttpServletResponse response = mvcResult.getResponse();
            // 获取字符串形式的响应内容
            String result = response.getContentAsString();
            
            System.out.println(result);
            
            // 异常对象
            //Exception exception = mvcResult.getResolvedException();
            
            
        } catch (Exception e) {
            log.error("testMockMVC =====> ", e);
        }
    }
    
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testMockMVC() {
		try {
			// 页面/服务 地址
			String url = prefix + "/";
			// 请求构建器
			// get 方法
			//MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
			// post 方法
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
			requestBuilder.header("Content-Type", "application/json;charset=UTF-8");
			requestBuilder.header("Accept", "application/json");
			/*
			 * 设置请求参数
			 */
			requestBuilder.param("username", "admin");
			
			// 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
			MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
			// mvc 结果
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			// 请求body
			//requestBuilder.content("{}");		
			
			// 响应对象
			MockHttpServletResponse response = mvcResult.getResponse();
			// 获取字符串形式的响应内容
			String result = response.getContentAsString();
			
			System.out.println(result);
			
			// 异常对象
			//Exception exception = mvcResult.getResolvedException();
			
			
		} catch (Exception e) {
			log.error("testMockMVC =====> ", e);
		}
	}
	
	/**
     * 
     * 描述: 文件上传
     * @author qye.zheng
     * 
     */
    @Test
    public void testUpload() {
        try {
            // 页面/服务 地址
            String url = prefix + "/upload";
            // 请求构建器
            // get 方法
            //MockHttpServletRequestBuilder requestBuilder = get(url);
            // 文件上传
            MockMultipartHttpServletRequestBuilder requestBuilder = fileUpload(url);
            requestBuilder.header("Accept", "application/json");
            /*
             * 设置请求参数
             */
            // 文件
            String path = null;
            InputStream inputStream = null;
            MockMultipartFile file = null;
            path = ClassPathUtil.getClassSubpath("/upload/upload.xlsx", true);
            inputStream = new FileInputStream(path);
            //file = new MockMultipartFile("file", "白熊_01.jpg", "application/octet-stream", inputStream);
            file = new MockMultipartFile("file", "啊哈abc.xlsx", "", inputStream);
            requestBuilder.file(file);
            // 响应对象
            MockHttpServletResponse response = perform(requestBuilder);
            // 获取字符串形式的响应内容
            String result = response.getContentAsString();
            System.out.println("响应数据:");
            System.out.println(result);
            
            // 异常对象
            //Exception exception = mvcResult.getResolvedException();
            
            
        } catch (Exception e) {
            log.error("testUpload =====> ", e);
        }
    }
    
    /**
     * 
     * 描述: 文件下载
     * @author qye.zheng
     * 
     */
    @Test
    public void testDownload() {
        try {
            // 页面/服务 地址
            String url = prefix + "/download";
            // 请求构建器
            // get 方法
            //MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
            // post 方法
            MockHttpServletRequestBuilder requestBuilder = post(url);
            requestBuilder.header("Content-Type", "application/json;charset=UTF-8");
            /*
             * 设置请求参数
             */
          
            
            // mvc 结果
            //MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
            // 响应对象
            MockHttpServletResponse response = perform(requestBuilder);
            
            OutputStream outputStream =new FileOutputStream(ProjectUtil.getAbsolutePath("/doc/a.xlsx", true));
            //outputStream.write(response.getContentAsByteArray());
            //outputStream.flush();
            IOUtils.write(response.getContentAsByteArray(), outputStream);
            
            //Exception exception = mvcResult.getResolvedException();
            
            
        } catch (Exception e) {
            log.error("testExport =====> ", e);
        }
    }
	
	/**
	 * 控制器 单元测试
	 * 描述: spring mvc 单元测试 (单元测试只能确保整个业务流程可以跑通)
	 * 直接注入控制器的方式只能做单元测试，不能做系统集成测试，
	 * 例如 可以走 spring mvc 过滤器链、类型转换、数据验证、数据绑定、拦截器等..
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testUnitController() {
		try {
			/**
			 * 构造 request / response 和参数对象
			 * 可以将此测试代码写在要测试的项目中，
			 * 也可以新建一个项目，然后引用需要测试的项目，
			 * 将 spring spring-mvc 系列环境启动起来，就可以测试了.
			 * dao / service / controller / tx / 数据源 ...
			 */
			HttpServletRequest request = new MockHttpServletRequest();
			HttpServletResponse response = new MockHttpServletResponse();
			//User user = new User();
			//user.setUsername("admin");
			//user.setPassword("123456");
			
			//userController.login(request, response, user);
			
		} catch (Exception e) {
			log.error("testInjectController =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testMockMVCExample() {
		try {
			// 页面/服务 地址
			String url = "/api/sys/login";
			// 请求构建器
			//RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
			// get 方法
			//MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
			// post 方法
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
			//String json = "{\"username\":\"admin\", \"password\":\"123456\"}";
			//requestBuilder.content(json);
			/*
			 * 设置请求参数
			 */
			requestBuilder.param("username", "admin");
			requestBuilder.param("password", "123456");
			//MockMvc mockMvc =  MockMvcBuilders.standaloneSetup(userController).build(); 
			
			// 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
			MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
			// mvc 结果
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			
			
			// 响应对象
			MockHttpServletResponse response = mvcResult.getResponse();
			// 获取字符串形式的响应内容
			String result = response.getContentAsString();
			System.out.println(result);
			
			//Map<String, Object> map = mvcResult.getModelAndView().getModel();
			//System.out.println(map);
			// 结果对象
			//Object resultObj = mvcResult.getAsyncResult();
			//System.out.println(resultObj.toString());
			
			// 异常对象
			//Exception exception = mvcResult.getResolvedException();
			
			
		} catch (Exception e) {
			log.error("testMockMVCExample =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
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
	@DisplayName("testTemp")
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
	@DisplayName("testCommon")
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
	@DisplayName("testSimple")
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
	@DisplayName("testBase")
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]开始之前运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("beforeMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@BeforeEach
	public void beforeMethod() {
		prefix = "/user";
		System.out.println("beforeMethod()");
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]结束之后运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("afterMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@AfterEach
	public void afterMethod() {
		System.out.println("afterMethod()");
	}
	
	/**
	 * 
	 * 描述: 测试忽略的方法
	 * @author qye.zheng
	 * 
	 */
	@Disabled
	@DisplayName("ignoreMethod")
	@Test
	public void ignoreMethod() {
		System.out.println("ignoreMethod()");
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("noUse")
	@Disabled("解决ide静态导入消除问题 ")
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
		assertArrayEquals(expecteds, actuals, message);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(true, message);
		assertTrue(true, message);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(expecteds, actuals, message);
		assertNotSame(expecteds, actuals, message);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(actuals, message);
		assertNotNull(actuals, message);
		
		fail();
		fail("Not yet implemented");
		
		dynamicTest(null, null);
		
	}

}
