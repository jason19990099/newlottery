package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/12/6.
 */

public class MsgDetailBean {

    private int code;
    private int msg;
    private MsgDetailContentBean content;

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

    public MsgDetailContentBean getContent() {
        return content;
    }

    public void setContent(MsgDetailContentBean content) {
        this.content = content;
    }

    public MsgDetailBean() {
        super();
    }

    public MsgDetailBean(int code, int msg, MsgDetailContentBean content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    @Override
    public String toString() {
        return "MsgDetailBean{" +
                "code=" + code +
                ", msg=" + msg +
                ", content=" + content +
                '}';
    }
}
