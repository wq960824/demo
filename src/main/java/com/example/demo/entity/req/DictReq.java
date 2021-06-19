package com.example.demo.entity.Req;

public class DictReq {
    private String type;

    private String code;


    private String codeEx;

    private String value;

    private Integer sort;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeEx() {
        return codeEx;
    }

    public void setCodeEx(String codeEx) {
        this.codeEx = codeEx;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
