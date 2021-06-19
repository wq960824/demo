package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.UserBase;
import com.example.demo.mapper.UserBaseMapper;
import com.example.demo.service.UserBaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基础信息 服务实现类
 * </p>
 *
 * @author wq
 * @since 2019-04-12
 */
@Service
public class UserBaseServiceImpl extends ServiceImpl<UserBaseMapper, UserBase> implements UserBaseService {

}
