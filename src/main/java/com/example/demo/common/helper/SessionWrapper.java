package com.example.demo.common.helper;

import org.apache.shiro.session.Session;

public class SessionWrapper {
    private Session session;

    private SessionWrapper(Session session) {
        this.session = session;
    }

    public static SessionWrapper wrap(Session session) {
        return new SessionWrapper(session);
    }

    public void save(Integer uid, String userName){
        session.setAttribute("sid",session.getId());
        session.setAttribute("uid", uid);
        session.setAttribute("userName", userName);
    }
    public String getSid() {
        return (String) session.getAttribute("sid");
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public Integer getUid() {
        return (Integer) session.getAttribute("uid");
    }

    /**
     * 获取用户名
     */
    public String getUserName() {
        return (String) session.getAttribute("userName");
    }

    public AppSession getLoginSession(){
        AppSession appSession = new AppSession();
        appSession.setSid(getSid());
        appSession.setUid(getUid());
        appSession.setUserName(getUserName());
        return appSession;
    }
}
