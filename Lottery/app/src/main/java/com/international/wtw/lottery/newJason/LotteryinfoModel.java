package com.international.wtw.lottery.newJason;

import java.io.Serializable;

public class LotteryinfoModel implements Serializable {

    private String Name;
    private String Code;
    private String ImageUrl;
    private boolean IsClose;

    public LotteryinfoModel(String name, String code, String ImageUrl, boolean IsClose) {
        this.Name=name;
        this.Code=code;
        this.ImageUrl=ImageUrl;
        this.IsClose=IsClose;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public boolean isClose() {
        return IsClose;
    }

    public void setClose(boolean close) {
        IsClose = close;
    }


}
