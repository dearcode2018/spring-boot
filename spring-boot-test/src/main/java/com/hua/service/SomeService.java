/**
  * @filename SomeService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @type SomeService
 * @description 
 * @author qianye.zheng
 */
@Service
public class SomeService {
    
    @Value("${a.b:-}")
    private String value;
    
    public void printValue() {
        System.out.println("value = " + value);
    }
    
}
