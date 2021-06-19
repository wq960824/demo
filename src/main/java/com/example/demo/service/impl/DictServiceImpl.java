package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Dict;
import com.example.demo.mapper.DictMapper;
import com.example.demo.service.DictService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author wq
 * @since 2019-08-08
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

}
