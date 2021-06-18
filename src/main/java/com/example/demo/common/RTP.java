package com.example.demo.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.enity.Code.StatusCode;

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
