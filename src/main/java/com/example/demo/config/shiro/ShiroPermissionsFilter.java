package com.example.demo.config.shiro;

import com.example.demo.common.RT;
import com.example.demo.entity.Code.StatusCode;
import com.example.demo.tools.HttpUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ShiroPermissionsFilter extends FormAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(ShiroPermissionsFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            return !this.isLoginSubmission(request, response) || this.executeLogin(request, response);
        } else {
            this.saveRequest(request);

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            logger.info("用户未登录,Request信息如下:");
            logger.info("Authorization内容" + httpServletRequest.getHeader("Authorization"));

            RT back = new RT(StatusCode.ERR, "用户未登录");
            HttpUtils.out(response, back);
            return false;
        }
    }
}
