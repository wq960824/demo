package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.common.RT;
import com.example.demo.entity.Dict;
import com.example.demo.entity.Req.DictReq;
import com.example.demo.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author wq
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    private  static  final Logger logger= LoggerFactory.getLogger(DictController.class);

    @Autowired
    private DictService dictService;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RT add(@RequestBody DictReq dictReq){
        logger.info("添加字典入参："+ JSON.toJSONString(dictReq));
        Dict dict=new Dict();
        BeanUtils.copyProperties(dictReq,dict);
        dictService.save(dict);
       return new RT();
    }
}
