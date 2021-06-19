package com.example.demo.common.exception;

public class CodeRuntimeException extends RuntimeException {

    private String code;

    public CodeRuntimeException(String code,String message) {
        super(message);
        this.code = code;
    }
    public CodeRuntimeException(Integer code,String message) {
        super(message);
        this.code = code.toString();
    }

    public String getCode() {
        return code;
    }
    @Override
    public String toString() {
        return String.format("%s@[code=%s, message=%s]", getClass().getName(), code, getMessage());
    }
}