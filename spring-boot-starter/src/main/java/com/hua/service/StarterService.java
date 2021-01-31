/**
  * @filename StarterService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import javax.annotation.Resource;

import com.hua.config.StarterConfigProperties;
import com.hua.util.JacksonUtil;

/**
 * @type StarterService
 * @description 
 * @author qianye.zheng
 */
public class StarterService extends CoreService {
    
    @Resource
    private StarterConfigProperties starterConfigProperties;
    
    public void showConfig(final boolean all) {
        if (all && log.isWarnEnabled()) {
            log.warn("config = {}", JacksonUtil.writeAsString(starterConfigProperties));
        } else {
            log.warn("name = {}", starterConfigProperties.getName());
        }
    }
    
}
