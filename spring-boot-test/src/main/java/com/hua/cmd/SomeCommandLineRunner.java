/**
  * @filename SomeCommandLineRunner.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.cmd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * @type SomeCommandLineRunner
 * @description 
 * @author qianye.zheng
 */
//@ConditionalOnProperty(value = "user.name", havingValue = "333", matchIfMissing = false)
// spring.active.profiles
//@Profile(value = {""})
@Component
public class SomeCommandLineRunner implements CommandLineRunner {
    
    /**
     * @description 
     * @param args
     * @throws Exception
     * @author qianye.zheng
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("SomeCommandLineRunner.run()");
    }
    
}
