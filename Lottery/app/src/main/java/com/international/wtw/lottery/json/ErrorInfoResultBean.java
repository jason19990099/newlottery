package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by wuya on 2017/6/14.
 */

public class ErrorInfoResultBean implements Serializable {
    /**
     * code : 1
     * msg : 4001
     */

    private int code;
    private String msg;

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
}
