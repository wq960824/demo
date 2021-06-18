package com.example.demo.common;

import com.example.demo.enity.Code.StatusCode;

public class RTD <T>{
    public String code;
    public String message;
    public T data;

    public RTD(T data) {
        this.code = StatusCode.SUCC;
        this.message = "OK";
        this.data = data;
    }

    public RTD(String code, String message,T data) {
        this.code = code;
        this.message = message;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
