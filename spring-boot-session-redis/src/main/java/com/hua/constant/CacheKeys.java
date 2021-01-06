/**
  * @filename CacheKeys.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.constant;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.hua.util.StringUtil;

/**
 * @type CacheKeys
 * @description 缓存Key集合
 * @author qianye.zheng
 */
public interface CacheKeys {
    
    /**
     * 
     * @type Lesson
     * @description 
     * @author qianye.zheng
     */
    enum Login implements CacheKey {
        
        /*登录用户ID */
        USER_ID,
        
        ;
        
    }
    
    /**
     * 
     * @type CacheKey
     * @description 缓存Key
     * @author qianye.zheng
     */
    interface CacheKey {
        default String getCacheKey(){
            String enumName = Arrays.stream(this.toString().split("_"))
                    .map(String::toUpperCase)
                    .collect(Collectors.joining(":"));

            String className = this.getClass().getSimpleName().toUpperCase();

            return new StringBuilder(className)
                    .append(":")
                    .append(enumName).toString();
        }

        default String getCacheKey(final Object... additionalSuffixs){
            final StringBuilder builder = StringUtil.getBuilder();
            builder.append(getCacheKey());
            for (Object suffix : additionalSuffixs) {
                builder.append(":" + suffix.toString());
            }
            
            return builder.toString();
        }
    }
    
}
