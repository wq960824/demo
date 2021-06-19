package com.example.demo.common;


import com.example.demo.entity.Code.StatusCode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public class RTP<T> extends Page<T> {
    public String code;

    public String message;

    public RTP() {
        this.code = StatusCode.SUCC;
        this.message = "OK";
    }

    public RTP(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
