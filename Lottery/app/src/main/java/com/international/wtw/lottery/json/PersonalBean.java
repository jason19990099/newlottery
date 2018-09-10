package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/10/27.
 */

public class PersonalBean extends BaseModel{

    private String userName;
    private String realName;
    private String balance;
    private String mailbox;
    private String telphone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public PersonalBean() {
        super();
    }

    public PersonalBean(String userName, String realName, String balance, String mailbox, String telphone) {
        this.userName = userName;
        this.realName = realName;
        this.balance = balance;
        this.mailbox = mailbox;
        this.telphone = telphone;
    }

    @Override
    public String toString() {
        return "PersonalBean{" +
                "userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", balance='" + balance + '\'' +
                ", mailbox='" + mailbox + '\'' +
                ", telphone='" + telphone + '\'' +
                '}';
    }
}
