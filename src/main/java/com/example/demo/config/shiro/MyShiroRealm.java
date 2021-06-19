package com.example.demo.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.UserBase;
import com.example.demo.mapper.UserBaseMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserBaseMapper userBaseMapper;
    @Autowired
    private SessionDAO sessionDAO;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginName = (String)authenticationToken.getPrincipal();
        SimpleAuthenticationInfo authenticationInfo;
        UsernamePasswordByTypeToken myToken = (UsernamePasswordByTypeToken)authenticationToken;
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        UserBase user=userBaseMapper.selectOne(new QueryWrapper<UserBase>().lambda().eq(UserBase::getUserName,loginName));
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(), //用户名
                user.getPass(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }
}
