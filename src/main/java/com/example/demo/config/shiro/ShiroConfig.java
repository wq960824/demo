package com.example.demo.config.shiro;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;



@Configuration

public class ShiroConfig {

    public static  final Logger logger= LoggerFactory.getLogger(ShiroConfig.class);
    //获取application.properties参数
    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;
    @Value("${spring.redis.shiro.timeout}")
    private int timeout;
    @Value("${spring.redis.shiro.password}")
    private String password;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();//获取filters
        //将自定义 的权限验证失败的过滤器ShiroFilterFactoryBean注入shiroFilter
        filters.put("authc", new ShiroPermissionsFilter());
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        //filterChainDefinitionMap.put("/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        //filterChainDefinitionMap.put("/swagger/**", "anon");
        //filterChainDefinitionMap.put("/v2/api-docs", "anon");
        //filterChainDefinitionMap.put("/swagger-ui.html", "anon");
           // filterChainDefinitionMap.put("/webjars/**", "anon");
        //filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/wns/**", "anon");
        filterChainDefinitionMap.put("/service/**", "anon");
        //filterChainDefinitionMap.put("/modeler.html", "anon");
       // filterChainDefinitionMap.put("/surety/**","anon");
        //filterChainDefinitionMap.put("/activity/**","anon");
        //filterChainDefinitionMap.put("/statics/**", "anon");
        //filterChainDefinitionMap.put("/editor-app/**", "anon");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        //shiroFilterFactoryBean.setLoginUrl("/wns/login");

        filterChainDefinitionMap.put("/**", "anon");

        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        return shiroFilterFactoryBean;
    }
    @Bean
    public MyShiroRealm myShiroRealm(MyCredentialsMatcher myCredentialsMatcher) {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(myCredentialsMatcher);
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager(MyShiroRealm myShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        //securityManager.setAuthenticator(defautModularRealm);
        // 自定义session管理 使用redis
       // securityManager.setSessionManager(sessionManager());
        //securityManager.setRememberMeManager();
        // 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
        return securityManager;
    }
    @Bean(name = "myCredentialsMatcher")
    public MyCredentialsMatcher credentialsMatcher() {
        MyCredentialsMatcher credentialsMatcher = new MyCredentialsMatcher();
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        credentialsMatcher.setHashAlgorithmName("SHA-256");//散列算法:这里使用MD5算法;
        credentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return credentialsMatcher;
    }

    public RedisManager redisManager() {
        logger.info("创建shiro redisManager,连接Redis..URL= " + host + ":" + port);
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // redisManager.setPassword(password)
        return redisManager;
    }

    public RedisCacheManager cacheManager() {
        logger.info("创建RedisCacheManager...");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO=new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return  redisSessionDAO;
    }

    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("sid");
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultCreate = new DefaultAdvisorAutoProxyCreator();
        defaultCreate.setProxyTargetClass(true);
        return defaultCreate;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        //mySessionManager.setGlobalSessionTimeout(18000000); // 30分钟
        mySessionManager.setDeleteInvalidSessions(true);
        mySessionManager.setSessionDAO(redisSessionDAO());
        mySessionManager.setSessionIdCookie(sessionIdCookie());
        mySessionManager.setSessionIdCookieEnabled(true);
        return mySessionManager;
    }

}

