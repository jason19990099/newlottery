package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by XIAOYAN on 2017/6/24.
 */

public class UserLoginMsgEvent implements Serializable {

    private String code;
    private String oid;
    private String username;
    private String realname;
    private String qqskype;
    private String money;
    private String telphone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getQqskype() {
        return qqskype;
    }

    public void setQqskype(String qqskype) {
        this.qqskype = qqskype;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public UserLoginMsgEvent() {
        super();
    }

    public UserLoginMsgEvent(String code, String oid, String username, String realname, String qqskype, String money, String telphone) {
        this.code = code;
        this.oid = oid;
        this.username = username;
        this.realname = realname;
        this.qqskype = qqskype;
        this.money = money;
        this.telphone = telphone;
    }

    @Override
    public String toString() {
        return "UserLoginMsgEvent{" +
                "code='" + code + '\'' +
                ", oid='" + oid + '\'' +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", qqskype='" + qqskype + '\'' +
                ", money='" + money + '\'' +
                ", telphone='" + telphone + '\'' +
                '}';
    }
}
