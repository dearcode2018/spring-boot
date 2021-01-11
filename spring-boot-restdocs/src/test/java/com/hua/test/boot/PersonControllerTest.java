/**
 * 描述: 
 * PersonControllerTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.boot;

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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hua.ApplicationStarter;
import com.hua.bean.PersonSearchBean;
import com.hua.test.BaseTest;
import com.hua.util.JacksonUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * PersonControllerTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
// for Junit 5.x
@ExtendWith(value = {RestDocumentationExtension.class, SpringExtension.class})
//@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@WebMvcTest
public final class PersonControllerTest extends BaseTest {

	
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
	//@Resource
	//private UserController userController;
	
	//@PathVariable
	
	//@Resource(type = WebApplicationContext.class)
	//@Autowired
	
	/**
	 * 引当前项目用其他项目之后，然后可以使用
	 * SpringJunitTest模板测试的其他项目
	 * 
	 * 可以使用所引用目标项目的所有资源
	 * 若引用的项目的配置与本地的冲突或无法生效，需要
	 * 将目标项目的配置复制到当前项目同一路径下
	 * 
	 */
    //@Rule    
    //private JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    
    private MockMvc mockMvc;
    
    /**
     * 
     * 描述: [每个测试-方法]开始之前运行
     * @author qye.zheng
     * 
     */
    @DisplayName("beforeMethod")
    @Tag(" [每个测试-方法]结束之后运行")
    @BeforeEach
    public void beforeMethod(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        prefix = "/person";
        System.out.println("beforeMethod()");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }
    
    /**
     * 
     * 描述: 
     * @author qye.zheng
     * 
     */
    @Test
    public void testRestDoc() {
        try {
            // 页面/服务 地址
            String url = prefix + "/search";
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
            PersonSearchBean searchBean = new PersonSearchBean();
            searchBean.setName("张三1");
            searchBean.setPassword("1234567");
            requestBuilder.content(JacksonUtil.writeAsString(searchBean));
            // 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
            //MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
            // mvc 结果
            mockMvc.perform(requestBuilder.param("name", searchBean.getName())).andDo(
                    MockMvcRestDocumentation.document("PersonController/search",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), 
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                            RequestDocumentation.parameterWithName("name").description("用户名"))
                    )
                  );
            // 请求body
            //requestBuilder.content("{}");     
            
            // 异常对象
            //Exception exception = mvcResult.getResolvedException();
            
            
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
    public void testRestDoc2() {
        try {
            // 页面/服务 地址
            String url = prefix + "/getAndPost";
            // 请求构建器
            // get 方法
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
            // post 方法
            //MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
            requestBuilder.header("Content-Type", "application/json;charset=UTF-8");
            requestBuilder.header("Accept", "application/json");
            /*
             * 设置请求参数
             */
            PersonSearchBean searchBean = new PersonSearchBean();
            searchBean.setName("张三1");
            searchBean.setPassword("1234567");
            requestBuilder.content(JacksonUtil.writeAsString(searchBean));
            // 模拟 mvc 对象，设置 WebApplicationContext，然后构建 模拟mvc对象
            //MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
            // mvc 结果
            mockMvc.perform(requestBuilder.param("name", searchBean.getName())).andDo(
                    MockMvcRestDocumentation.document("PersonController/getAndPost",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), 
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.requestParameters(
                            RequestDocumentation.parameterWithName("name").description("用户名"))
                    )
                  );
            // 请求body
            //requestBuilder.content("{}");     
            
            // 异常对象
            //Exception exception = mvcResult.getResolvedException();
            
            
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
