package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by XIAOYAN on 2017/6/26.
 */

public class UserLoginMsgErrorEvent implements Serializable {

    private int code;
    private String msg;
    private String begin;
    private String end;

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

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "UserLoginMsgErrorEvent{" +
                "code=" + code +
                ", msg=" + msg +
                '}';
    }
}
