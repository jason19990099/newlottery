package com.international.wtw.lottery.json;

/**
 * Created by Abin on 2017/9/9.
 * 描述：用户登录信息
 */

public class UserModel extends BaseModel {


    /**
     * oid : 59463f7f0f53366ef243eb1145e0924f
     * username : qqq111222
     * realname : 123
     * qqskype :
     * telphone :
     * money : 0.24
     * rate_version : 66
     * a : 22
     * position : A
     * environment : production
     */

    private String oid;
    private String username;
    private String realname;
    private String qqskype;
    private String telphone;
    private String money;
    private String rate_version;
    private String a;
    private String position;
    private String environment;

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

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRate_version() {
        return rate_version;
    }

    public void setRate_version(String rate_version) {
        this.rate_version = rate_version;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "oid='" + oid + '\'' +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", qqskype='" + qqskype + '\'' +
                ", telphone='" + telphone + '\'' +
                ", money=" + money +
                ", rate_version=" + rate_version +
                ", a='" + a + '\'' +
                ", position='" + position + '\'' +
                ", environment='" + environment + '\'' +
                '}';
    }
}
