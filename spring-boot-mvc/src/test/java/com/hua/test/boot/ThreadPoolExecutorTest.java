/**
 * 描述: 
 * ThreadPoolExecutorTest.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.hua.ApplicationStarter;
import com.hua.test.BaseTest;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * ThreadPoolExecutorTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
// for Junit 5.x
@ExtendWith(SpringExtension.class)
@WebAppConfiguration(value = "src/main/webapp")
@SpringBootTest(classes = {ApplicationStarter.class}, 
webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@MapperScan(basePackages = {"com.hua.mapper"})
public final class ThreadPoolExecutorTest extends BaseTest {

	
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
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	
	private List<Thread> threads = new ArrayList<>(10);
	
	@Resource
	private RestTemplate restTemplate;
	
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
    //@DisplayName("test")
    @Test
    public void testShutdown() {
        try {
            System.out.println("do something");
            threadPoolTaskExecutor.submit(new SomeTask());
            //threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(false);
            
            threadPoolTaskExecutor.shutdown();
            threads.forEach(x -> x.interrupt());
            //threadPoolTaskExecutor.getThreadPoolExecutor().shutdownNow();
            // 中断线程组
            //threadPoolTaskExecutor.getThreadGroup().destroy();
            
            System.out.println("do otherthing");
            
        } catch (Exception e) {
            log.error("test =====> ", e);
        }
    }
	
    /**
     * 
     * @type SomeTask
     * @description 
     * @author qianye.zheng
     */
    class SomeTask implements Callable<String> {
        /**
         * @description 
         * @return
         * @throws Exception
         * @author qianye.zheng
         */
        @Override
        public String call() throws Exception {
            System.out.println("do some task");
            // 打断点 或 其他阻塞模拟
            try {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) { // 线程池shutdown()，各个线程收到中断信号
                        return "stop signal";
                    }
                    //TimeUnit.MILLISECONDS.sleep(200);
                    System.out.println("do other task");
                }
            } catch (Exception e) {
                log.error("发生异常: {}", e);
            }
            System.out.println("finish quickly");
            
            return "normal signal";
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
    public void testShutdownWithInterrupt() {
        try {
            System.out.println("do something");
            threadPoolTaskExecutor.submit(new SomeTask2());
            //threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(false);
            
            threads.forEach(x -> {
                System.out.println("id1 = " + x.getId());
                x.interrupt();
                
            });
            threadPoolTaskExecutor.shutdown();
            //threadPoolTaskExecutor.getThreadPoolExecutor().shutdownNow();
            // 中断线程组
            //threadPoolTaskExecutor.getThreadGroup().destroy();
            
            System.out.println("do otherthing");
            
        } catch (Exception e) {
            log.error("test =====> ", e);
        }
    }   
    
    /**
     * 
     * @type SomeTask
     * @description 
     * @author qianye.zheng
     */
    class SomeTask2 implements Callable<String> {
        /**
         * @description 
         * @return
         * @throws Exception
         * @author qianye.zheng
         */
        @Override
        public String call() throws Exception {
            threads.add(Thread.currentThread());
            System.out.println("id2 = " + Thread.currentThread().getId());
            System.out.println("do some task");
            // 打断点 或 其他阻塞模拟
            try {
                 System.out.println("do other task");
                 url = "http://localhost:7070/hello/get";
                 // IO阻塞
                 restTemplate.getForEntity(url, String.class, "123");
                 
            } catch (Exception e) {
                log.error("发生异常: {}", e);
            }
            System.out.println("finish quickly");
            
            return "normal signal";
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
            url = "http://localhost:7070/hello/get";
            SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
            // IO阻塞
            restTemplate.getForEntity(url, String.class, "123");
			
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
