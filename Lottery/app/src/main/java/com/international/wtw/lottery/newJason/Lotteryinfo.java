package com.international.wtw.lottery.newJason;

import java.io.Serializable;

public class Lotteryinfo implements Serializable {

    private String name;
    private String gameTypeCode;
    private String code;
    private int pic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameTypeCode() {
        return gameTypeCode;
    }

    public void setGameTypeCode(String gameTypeCode) {
        this.gameTypeCode = gameTypeCode;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Lotteryinfo(String name, String gameTypeCode,String code, int pic) {
        this.name = name;
        this.code=code;
        this.gameTypeCode = gameTypeCode;
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Lotteryinfo{" +
                "name='" + name + '\'' +
                ", gameTypeCode='" + gameTypeCode + '\'' +
                ", pic=" + pic +
                '}';
    }
}
