/**
  * @filename SomeThreadController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hua.bean.ResultBean;

/**
 * @type SomeThreadController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping("/some")
public class SomeThreadController extends BaseController {
    
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
    private List<Thread> threads = new ArrayList<>(10);
    
    @Resource
    private RestTemplate restTemplate;
    
    /**
     * 
     * @description 
     * @param code
     * @return
     * @author qianye.zheng
     */
    @GetMapping(path = {"/task"})
    public ResultBean get()
    {
        ResultBean result = new ResultBean();
        result.setMessageCode("205");
        result.setSuccess(true);
        // 提交任务
        threadPoolTaskExecutor.submit(new SomeTask2());
        
        return result;
    }
    
    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    @GetMapping(path = {"/shutdown"})
    public ResultBean shutdown() {
        ResultBean result = new ResultBean();
        result.setMessageCode("205");
        result.setSuccess(true);
        // 关闭线程池
        threadPoolTaskExecutor.shutdown();
        
        return result;
    }
    
    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    @GetMapping(path = {"/interrupt"})
    public ResultBean interrupt() {
        ResultBean result = new ResultBean();
        result.setMessageCode("205");
        result.setSuccess(true);
        // 对线程对象发出中断信号
        threads.forEach(x -> {
            x.interrupt();
        });
        
        return result;
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
            System.out.println("do some task");
            // 打断点 或 其他阻塞模拟
            try {
                 System.out.println("do other task");
                 String url = "http://localhost:7070/hello/get";
                 // IO阻塞
                 restTemplate.getForEntity(url, String.class, "123");
                 
            } catch (Exception e) {
                log.error("发生异常: {}", e);
            }
            System.out.println("finish quickly");
            
            return "normal signal";
        }
    }
}
