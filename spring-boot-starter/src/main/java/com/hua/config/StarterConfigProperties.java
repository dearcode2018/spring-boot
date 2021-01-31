/**
  * @filename StarterConfigProperties.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @type StarterConfigProperties
 * @description 配置属性
 * @author qianye.zheng
 */
@ConfigurationProperties(prefix = "starter.config")
@Data
public class StarterConfigProperties {
    
    /* 是否开启功能 */
    private boolean open;
    
    private String name;
    
    private int number;
    
    
}
