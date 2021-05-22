/**
  * @filename ConditionOnValue.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @type ConditionOnValue
 * @description 
 * @author qianye.zheng
 */
@ConditionalOnProperty(value = "conditional.on.property", matchIfMissing = true, havingValue = "true")
@Configuration
public class ConditionOnValue {

	
}
