package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/12/6.
 */

public class MsgDetailContentBean {

    private String id;
    private String comment;
    private String addtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public MsgDetailContentBean() {
        super();
    }

    public MsgDetailContentBean(String id, String comment, String addtime) {
        this.id = id;
        this.comment = comment;
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "MsgDetailContentBean{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
