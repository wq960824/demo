package com.example.demo.common;


import com.example.demo.entity.Code.StatusCode;

public class RT {

    public String code;

    public String message;


    public RT(){
        this.code = StatusCode.SUCC;
        this.message = "OK";
    }

    public RT(String code, String message) {
        this.code = code;
        this.message = message;
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
