package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.RT;
import com.example.demo.common.RTP;
import com.example.demo.entity.Req.SysRoleReq;
import com.example.demo.entity.SysRole;
import com.example.demo.service.SysRoleService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 所有的角色 前端控制器
 * </p>
 *
 * @author wq
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RTP<SysRole> getList(int current, int size) {
        Page<SysRole> page = new Page<>(current, size);
        IPage<SysRole> iPage =sysRoleService.page(page,new QueryWrapper<>());
        RTP<SysRole> sysRoleRTP=new RTP<>();
        BeanUtils.copyProperties(iPage,sysRoleRTP);
        return sysRoleRTP;
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RT add(@RequestBody SysRoleReq sysRoleReq){
        SysRole sysRole=new SysRole();
        sysRole.setRole(sysRoleReq.getRole());
        sysRole.setName(sysRoleReq.getName());
        sysRole.setBelong(sysRoleReq.getBelong());
        sysRoleService.save(sysRole);
        return new RT();
    }
}
