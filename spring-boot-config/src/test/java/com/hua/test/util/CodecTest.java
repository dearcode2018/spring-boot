/**
 * 描述: 
 * CodecTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.util;

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

import com.hua.test.BaseTest;
import com.hua.util.StringUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * CodecTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
public final class CodecTest extends BaseTest {

	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testToUniCode() {
		try {
			String str = "张三";
			System.out.println();
		} catch (Exception e) {
			log.error("testToUniCode =====> ", e);
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
	public void printASCIICode() {
		try {
			
			
			char ch = 0;
			for (int i = 0; i < 128; i++) {
				System.out.printf("ASCII[ %d ] = %c", i, ch);
				System.out.println();
				ch++;
			}
		
			/*
可视字符输出如下: [0, 127]
ASCII[ 12 ] = 
ASCII[ 33 ] = !
ASCII[ 34 ] = "
ASCII[ 35 ] = #
ASCII[ 36 ] = $
ASCII[ 37 ] = %
ASCII[ 38 ] = &
ASCII[ 39 ] = '
ASCII[ 40 ] = (
ASCII[ 41 ] = )
ASCII[ 42 ] = *
ASCII[ 43 ] = +
ASCII[ 44 ] = ,
ASCII[ 45 ] = -
ASCII[ 46 ] = .
ASCII[ 47 ] = /
ASCII[ 48 ] = 0
ASCII[ 49 ] = 1
ASCII[ 50 ] = 2
ASCII[ 51 ] = 3
ASCII[ 52 ] = 4
ASCII[ 53 ] = 5
ASCII[ 54 ] = 6
ASCII[ 55 ] = 7
ASCII[ 56 ] = 8
ASCII[ 57 ] = 9
ASCII[ 58 ] = :
ASCII[ 59 ] = ;
ASCII[ 60 ] = <
ASCII[ 61 ] = =
ASCII[ 62 ] = >
ASCII[ 63 ] = ?
ASCII[ 64 ] = @
ASCII[ 65 ] = A
ASCII[ 66 ] = B
ASCII[ 67 ] = C
ASCII[ 68 ] = D
ASCII[ 69 ] = E
ASCII[ 70 ] = F
ASCII[ 71 ] = G
ASCII[ 72 ] = H
ASCII[ 73 ] = I
ASCII[ 74 ] = J
ASCII[ 75 ] = K
ASCII[ 76 ] = L
ASCII[ 77 ] = M
ASCII[ 78 ] = N
ASCII[ 79 ] = O
ASCII[ 80 ] = P
ASCII[ 81 ] = Q
ASCII[ 82 ] = R
ASCII[ 83 ] = S
ASCII[ 84 ] = T
ASCII[ 85 ] = U
ASCII[ 86 ] = V
ASCII[ 87 ] = W
ASCII[ 88 ] = X
ASCII[ 89 ] = Y
ASCII[ 90 ] = Z
ASCII[ 91 ] = [
ASCII[ 92 ] = \
ASCII[ 93 ] = ]
ASCII[ 94 ] = ^
ASCII[ 95 ] = _
ASCII[ 96 ] = `
ASCII[ 97 ] = a
ASCII[ 98 ] = b
ASCII[ 99 ] = c
ASCII[ 100 ] = d
ASCII[ 101 ] = e
ASCII[ 102 ] = f
ASCII[ 103 ] = g
ASCII[ 104 ] = h
ASCII[ 105 ] = i
ASCII[ 106 ] = j
ASCII[ 107 ] = k
ASCII[ 108 ] = l
ASCII[ 109 ] = m
ASCII[ 110 ] = n
ASCII[ 111 ] = o
ASCII[ 112 ] = p
ASCII[ 113 ] = q
ASCII[ 114 ] = r
ASCII[ 115 ] = s
ASCII[ 116 ] = t
ASCII[ 117 ] = u
ASCII[ 118 ] = v
ASCII[ 119 ] = w
ASCII[ 120 ] = x
ASCII[ 121 ] = y
ASCII[ 122 ] = z
ASCII[ 123 ] = {
ASCII[ 124 ] = |
ASCII[ 125 ] = }
ASCII[ 126 ] = ~
			 */
			
		} catch (Exception e) {
			log.error("printASCIICode =====> ", e);
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
	public void testUnicode() {
		try {
			String str = "广州";
			//System.out.println((char) str.codePointAt(0));
			//System.out.println((char) str.codePointAt(1));
			//System.out.println((char) 24191);
			
			str = "张三";
			System.out.println(StringUtil.chineseToUnicode(str));
			
			str = "李四";
			System.out.println(StringUtil.chineseToUnicode(str));
			
		} catch (Exception e) {
			log.error("testUnicode =====> ", e);
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
