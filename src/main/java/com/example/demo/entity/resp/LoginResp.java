package com.example.demo.entity.Resp;

import com.example.demo.common.helper.AppSession;



public class LoginResp {
    private String code;

    private String message;

    private AppSession data;

    public AppSession getData() {
        return data;
    }

    public void setData(AppSession data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
