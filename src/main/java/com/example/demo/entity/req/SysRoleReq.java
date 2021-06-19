package com.example.demo.entity.Req;

public class SysRoleReq {

    private String role;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色的归属，ZONGBU:总部，GONGSI:公司, ZIFANG:资方
     */
    private String belong;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }
}
