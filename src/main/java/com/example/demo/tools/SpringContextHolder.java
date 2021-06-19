package com.example.demo.tools;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


@Service(value = "serviceTaskService")
public class SpringContextHolder<unchecked> implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext=null;
    private static Logger logger= LoggerFactory.getLogger(SpringContextHolder.class);

    public static void assertContextInjected(){
        Validate.validState(applicationContext!=null,"属性未注入");
    }
    public static ApplicationContext getApplicationContext(){
        assertContextInjected();
        return applicationContext;

    }
    public static <T> T getBean(String name){
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }
    public static <T> T getBean(Class<T> requirdType){
        assertContextInjected();
        return applicationContext.getBean(requirdType);
    }
    public static void clearHolder(){
        logger.debug("清除"+applicationContext);
        applicationContext=null;
    }
    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext!=null){
            logger.warn("ApplocationContext被覆盖 原有的为"+SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext=applicationContext;
    }
}
