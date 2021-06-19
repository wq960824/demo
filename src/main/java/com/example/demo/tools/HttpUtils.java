package com.example.demo.tools;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.exception.CodeRuntimeException;
import com.example.demo.entity.Code.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);



    public static void out(ServletResponse response, Object back) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            String jsonStr = JSON.toJSONString(back);
            out.write(jsonStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String post(String url,  Map<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate=new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
            String body=response.getBody();
            return body;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CodeRuntimeException(StatusCode.ERR, url + "当前网络繁忙，请稍后重试");

        }
    }

    public static String get(String url,  Map<String, String> map) {
        RestTemplate restTemplate=new RestTemplate();
        try {
            String response = restTemplate.getForObject( url, String.class ,map);
            return response;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CodeRuntimeException(StatusCode.ERR, url + "当前网络繁忙，请稍后重试");

        }
    }

    public static String get(String url,HttpHeaders headers ){
        RestTemplate restTemplate=new RestTemplate();
        Map<String, Object> map=new HashMap<>();
        HttpEntity< Map<String, Object>> request = new HttpEntity<>(map, headers);
        try {
            ResponseEntity responseEntity =restTemplate.exchange(url, HttpMethod.GET,request,String.class);
            return responseEntity.getBody().toString();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CodeRuntimeException(StatusCode.ERR, url + "当前网络繁忙，请稍后重试");
        }

    }
}
