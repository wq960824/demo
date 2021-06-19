package com.example.demo.tools;

import com.example.demo.common.exception.CodeRuntimeException;
import com.example.demo.entity.Code.StatusCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
    /**  加密算法 */
    public final static String hashAlgorithmName = "SHA-256";
    /**  循环次数 */
    public final static int hashIterations = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);

        System.out.println("kaptcha:"+kaptcha);

        if(kaptcha == null){
            throw new CodeRuntimeException(StatusCode.ERR, "验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }
}
