package com.international.wtw.lottery.json;

import com.international.wtw.lottery.base.Constants;

import java.io.Serializable;

/**
 * Created by Abin on 2017/9/9.
 * 描述：Model基类 其他model需继承该类
 */

public class BaseModel implements Serializable {

    /**
     * 状态码
     */
    protected int code;

    /**
     * 错误信息
     */
    protected String msg;

    protected String info;

    protected String begin;

    protected String end;

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

    public String getErrorCodeInfo() {
        return Constants.getErrorCodeInfo(msg);
    }
}
