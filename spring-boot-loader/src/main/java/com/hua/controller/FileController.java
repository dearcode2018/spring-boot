/**
  * @filename FileController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hua.bean.ResultBean;
import com.hua.util.IOUtil;

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
    
    /**
     * 
     * @description 下载文件
     * @param request
     * @param response
     * @author qianye.zheng
     */
    @GetMapping("/v1/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*
         * 验证打包成jar包的时候，文件的下载
         * 打成jar包之后，文件路径获取方式和不打包方式是不同的
         */
        // 用流的方式去获取
        final InputStream inputStream = getClass().getResourceAsStream("/template/questionnaire.xlsx");
        String location = null;
        location = "classpath:template/2007表格.xlsx";
        //location = "classpath:template/2003表格.xls";
        //ResourceLoader resourceLoader = new DefaultResourceLoader();
        //Resource resource = resourceLoader.getResource(location);
        
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("表格1.xlsx".getBytes(), StandardCharsets.ISO_8859_1));
        //InputStream inputStream = resource.getInputStream();
        IOUtils.copy(inputStream, response.getOutputStream());
        //IOUtil.transport(inputStream, response.getOutputStream());
        //response.addHeader("Content-Length", "" + buffer.length);
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");

        
        //System.out.println("文件大小: " + inputStream.available());
        //System.out.println(path);
        /*
         * try {
         * IOUtil.transport(inputStream, response.getOutputStream());
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         */
        download(response, inputStream, "表格1.xlsx");
    }
    
    /**
     * 根据文件路径下载表格模板文件
     * @param filePath
     */
    private void download(HttpServletResponse response, final InputStream inputStream, final String filename) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), StandardCharsets.ISO_8859_1));
            response.addHeader("Content-Length", "" + buffer.length);
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
