package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.RTD;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.SysUserRole;
import com.example.demo.service.SysRoleService;
import com.example.demo.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户的角色 前端控制器
 * </p>
 *
 * @author wq
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @RequestMapping(value = "/getRole",method = RequestMethod.GET)
    public RTD<SysRole> getRole(int uid){
        List<SysUserRole> userRoleList =sysUserRoleService.list(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUid,uid));
        List<SysRole> sysRoles=new ArrayList<>();
        for (SysUserRole sysUserRole:userRoleList){
            SysRole sysRole=sysRoleService.getById(sysUserRole.getRoleId());
            if (sysRole!=null){
                sysRoles.add(sysRole);
            }

        }
        return new RTD(sysRoles);
    }

}
