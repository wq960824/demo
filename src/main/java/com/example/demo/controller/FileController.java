package com.example.demo.controller;


import com.example.demo.common.RT;
import com.example.demo.service.FileService;
import com.example.demo.common.exception.CodeRuntimeException;
import com.example.demo.entity.Code.StatusCode;
import com.example.demo.entity.File;
import com.example.demo.service.FileService;
import com.example.demo.tools.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * 系统文件库（图片、视频等） 前端控制器
 * </p>
 *
 * @author wq
 * @since 2019-08-02
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private   static final Logger logger= LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public RT upLoad(@RequestParam("file") MultipartFile file){
        logger.info("开始上传文件");
        File fileAttestation=new File();
        try {
            String fileDir = FileUploadUtil.upload(java.io.File.separator+"upload" , file);
            String fileName = file.getOriginalFilename();
            fileAttestation.setPath(fileDir);
            fileAttestation.setSize(new Long(file.getSize()).intValue());
            fileAttestation.setType(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
            fileAttestation.setName(fileName);
            fileService.save(fileAttestation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RT();
    }
    @RequestMapping(value = "down",method = RequestMethod.GET)
    public void down(Integer fid, HttpServletResponse response){
        logger.info("开始下载");
        File file=new File();
        ServletOutputStream out = null;
        FileInputStream ips = null;
        file=fileService.getById(fid);
        if (file!=null){
            String fileName = null;
            try {
                fileName = URLEncoder.encode(file.getName(), "UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader("Cache-Control", "no-cache");
                java.io.File file1=new java.io.File(file.getPath());
                OutputStream outputStream = response.getOutputStream();
                if (file1.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(file1);
                    byte[] b = new byte[1024];
                    int length;
                    while ((length = fileInputStream.read(b)) != -1) {
                        outputStream.write(b, 0, length);
                        outputStream.flush();
                    }
                } else {
                    throw new CodeRuntimeException(StatusCode.ERR, "无此文件");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
