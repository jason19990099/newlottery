package com.international.wtw.lottery.json;


import java.io.Serializable;

/**
 * 描述：Model基类 其他model需继承该类
 */

public class BaseModel implements Serializable {


    protected int code;
    protected String msg;
    protected String info;
    protected boolean status;
    protected int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
