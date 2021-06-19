package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.RT;
import com.example.demo.common.exception.CodeRuntimeException;
import com.example.demo.common.helper.SessionWrapper;
import com.example.demo.config.shiro.PasswordHelper;
import com.example.demo.config.shiro.UsernamePasswordByTypeToken;
import com.example.demo.entity.Code.StatusCode;
import com.example.demo.entity.Req.LoginReq;
import com.example.demo.entity.Req.UserBaseReq;
import com.example.demo.entity.Resp.LoginResp;
import com.example.demo.entity.UserBase;
import com.example.demo.service.UserBaseService;
import com.example.demo.tools.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/wns")
public class AuthController {

    private   static final Logger logger= LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private Producer producer;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginResp login(@RequestBody LoginReq loginReq)throws Exception{
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordByTypeToken token = new UsernamePasswordByTypeToken(loginReq);
        LoginResp loginResp=new LoginResp();
        try {
            subject.login(token);
        }
        catch (UnknownAccountException e) {
            loginResp.setCode(StatusCode.ERR);
            loginResp.setMessage("无此账号");
            return loginResp;
        }catch (AuthenticationException e) {
            e.printStackTrace();
            loginResp.setCode(StatusCode.ERR);
            loginResp.setMessage("验证失败");
            return loginResp;
        }
        loginResp.setCode(StatusCode.SUCC);
        loginResp.setMessage("成功登陆");
        SessionWrapper sessionWrapper=SessionWrapper.wrap(ShiroUtils.getSession());
        UserBase ub=userBaseService.getOne(new QueryWrapper<UserBase>().lambda().eq(UserBase::getUserName,loginReq.getUserName()));
        sessionWrapper.save(ub.getUid(),ub.getUserName());
        loginResp.setData(sessionWrapper.getLoginSession());
        return loginResp;
    }
    @RequestMapping(value = "/loginout", method = RequestMethod.POST)
    public RT logout() {
        SecurityUtils.getSubject().logout();

        return new RT(StatusCode.SUCC, "退出登录成功");
    }
    @RequestMapping(value = "/registered",method = RequestMethod.POST)
    //目前只写入后台用户注册如果要加入手机用户注册的请重新编写接口
    public RT registered(@RequestBody UserBaseReq userBaseReq){
        logger.info("注册用户入参"+ JSON.toJSONString(userBaseReq));
        //账号密码是否输入前台判空后台这边目前不做处理只为了实现功能需要后台处理以后自行添加代码
        //用户名目前不允许相同
        //
        UserBase user=userBaseService.getOne(new QueryWrapper<UserBase>().lambda().eq(UserBase::getUserName,userBaseReq.getUserName()));
        if (user!=null){
            return new RT(StatusCode.SUCC,"存在相同用户名");
        }
        UserBase userBase=new UserBase();
        userBase.setUserName(userBaseReq.getUserName());
        userBase.setMobile(userBaseReq.getMobile());
        userBase.setPass(userBaseReq.getPass());
        userBase.setUserCate("admin");
        //因为不考虑修改员工的其他内容如果后期需要在此接口上进行修改上面代码可以修改为,考虑到上面的判断此接口还是只作为注册接口修改请另外开接口
        //BeanUtils.copyProperties(user,userBase);
        //同理不对是否有密码进行判断默认输入密码
        PasswordHelper.encryptPassword(userBase);
        userBaseService.save(userBase);
        //其他用户基础信息根据需求自己改造
        return new RT();
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        logger.info("图片验证码：" + text);
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        SecurityUtils.getSubject().getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }
    @RequestMapping(value = "/yzcaptcha",method = RequestMethod.GET)
    public RT yz(String ms){
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (ms.equals(kaptcha)){
            return new RT();
        }else {
            throw new CodeRuntimeException(StatusCode.ERR, "图片验证码不正确");
        }
    }


}
