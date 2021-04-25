package com.example.demo02app.model.mine.data;

public class IdentityRegister {

    /**
     * 用户权限
     */
    private int identity;
    /**
     *真实姓名
     */
    private String realName;
    /**
     * 用户职位
     */
    private String position;

    public IdentityRegister() {
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

