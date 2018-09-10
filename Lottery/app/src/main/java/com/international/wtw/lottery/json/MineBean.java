package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by XIAOYAN on 2017/8/14.
 */

public class MineBean implements Serializable {

    private String type_name;
    private String type_img;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_img() {
        return type_img;
    }

    public void setType_img(String type_img) {
        this.type_img = type_img;
    }

    public MineBean() {
        super();
    }

    public MineBean(String type_name, String type_img) {
        this.type_name = type_name;
        this.type_img = type_img;
    }

    @Override
    public String toString() {
        return "MineBean{" +
                "type_name='" + type_name + '\'' +
                ", type_img='" + type_img + '\'' +
                '}';
    }
}
