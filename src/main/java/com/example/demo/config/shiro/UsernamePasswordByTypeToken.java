package com.example.demo.config.shiro;


import com.example.demo.entity.Req.LoginReq;
import org.apache.shiro.authc.UsernamePasswordToken;


public class UsernamePasswordByTypeToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;
    private Integer loginType;
    private String smsCaptcha;

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

    public Integer getLoginType() {
        return loginType;
    }
    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public UsernamePasswordByTypeToken(LoginReq loginReq) {
        super(loginReq.getUserName(), loginReq.getPassword());
        this.loginType = loginReq.getLoginType();
        this.smsCaptcha = loginReq.getSmsCaptcha();
    }

}
