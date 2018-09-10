package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/10/14.
 */

public class ErrorPhoneBean {

    private int code;
    private int msg;
    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ErrorPhoneBean(int code, int msg, String info) {
        this.code = code;
        this.msg = msg;
        this.info = info;
    }

    public ErrorPhoneBean() {
        super();
    }

    @Override
    public String toString() {
        return "ErrorPhoneBean{" +
                "code=" + code +
                ", msg=" + msg +
                ", info='" + info + '\'' +
                '}';
    }
}
