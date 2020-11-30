/**
  * @filename FileController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hua.bean.ResultBean;

/**
 * @type FileController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    
    
    /**
     * 
     * @description 
     * @param file
     * @param request
     * @return
     * @author qianye.zheng
     */
    @PostMapping("/upload")
    public ResultBean upload(final@RequestParam("file")  MultipartFile file, final HttpServletRequest request) {
        final String str = request.getParameter("file");
        final String filename = request.getParameter("file.filename");
       try {
        System.out.println(file.getResource().getFile().getAbsoluteFile().getPath());
    } catch (IOException e) {
        e.printStackTrace();
    }
        
        return null;
    }
    
    
}
