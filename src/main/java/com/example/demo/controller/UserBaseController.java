package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.RT;
import com.example.demo.config.shiro.PasswordHelper;
import com.example.demo.entity.Code.StatusCode;
import com.example.demo.entity.Req.ChangePassReq;
import com.example.demo.entity.SysUserRole;
import com.example.demo.entity.UserBase;
import com.example.demo.service.SysUserRoleService;
import com.example.demo.service.UserBaseService;
import com.example.demo.tools.RandStrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 用户基础信息 前端控制器
 * </p>
 *
 * @author wq
 * @since 2019-04-12
 */
@RestController
@RequestMapping("/userBase")
public class UserBaseController {

    private static  final Logger logger= LoggerFactory.getLogger(UserBaseController.class);

    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @RequestMapping(value = "/reset/{uid}", method = RequestMethod.POST)
    public RT resetPass(@PathVariable("uid") int uid) {
        logger.info("重置密码传入参数 uid"+uid);
        UserBase userBase = userBaseService.getById(uid);
        String pass = RandStrUtil.genNumeric(6);
        //String pass="123456";
        userBase.setPass(pass);
        PasswordHelper.encryptPassword(userBase);
        userBaseService.updateById(userBase);
        SecurityUtils.getSubject().logout();
        logger.info("密码更新为 pass"+pass);
        return new RT(StatusCode.SUCC, "重置密码成功，新密码是" + pass);
    }
    @RequestMapping(value = "/changepass",method = RequestMethod.POST)
    public RT changepass(@RequestBody ChangePassReq changePassReq){
        logger.info("修改用户自身密码入参"+ JSON.toJSONString(changePassReq));
        //因为现在未整合session修改自身密码此接口数据需要前端传入对应的参数，等后期改造
        UserBase user=userBaseService.getById(changePassReq.getUid());
        String oldpass=new SimpleHash(
                "SHA-256",
                changePassReq.getOldPass(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                1024).toBase64();
        if (oldpass.equals(user.getPass())){
            user.setPass(changePassReq.getNewPass());
            PasswordHelper.encryptPassword(user);
            userBaseService.updateById(user);
            return new RT(StatusCode.SUCC, "修改密码成功");
        }else {
            return new RT(StatusCode.ERR, "原密码错误");

        }
    }
    @RequestMapping(value = "/addUserRole",method = RequestMethod.GET)
    public RT addUserRole(int uid,String roles){
        List<SysUserRole> list=new ArrayList<>();
        //roles是以逗号隔开的权限集合
        String[] arr =roles.split(",");
        for (String role:arr){
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setUid(uid);
            sysUserRole.setRoleId(Integer.valueOf(role));
            list.add(sysUserRole);
        }
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUid,uid));
        sysUserRoleService.saveBatch(list);
        return new RT();
    }
}
