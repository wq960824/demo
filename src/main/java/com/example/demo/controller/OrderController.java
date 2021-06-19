package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.RTP;
import com.example.demo.entity.Order;
import com.example.demo.service.FileService;
import com.example.demo.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * <p>
 *
 * </p>
 *
 * @author wq
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private FileService fileService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String getList(int current, int size, HttpServletRequest request){
        Page<Order> page=new Page<>(current,size);
        QueryWrapper queryWrapper=new QueryWrapper();
        IPage<Order> iPage =orderService.page(page,queryWrapper);
        RTP<Order> orderRTP=new RTP<>();
        BeanUtils.copyProperties(iPage,orderRTP);
        String s="";
        try {
            s=URLDecoder.decode(request.getHeader("cid"), "UTF-8");
            s=request.getHeader("a");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return s;
    }

}
