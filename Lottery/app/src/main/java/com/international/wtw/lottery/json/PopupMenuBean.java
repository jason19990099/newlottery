package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2018/2/13.
 */

public class PopupMenuBean {

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PopupMenuBean() {
        super();
    }

    public PopupMenuBean(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PopupMenuBean{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
