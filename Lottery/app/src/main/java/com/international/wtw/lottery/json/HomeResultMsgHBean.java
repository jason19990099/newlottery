package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by XIAOYAN on 2017/7/27.
 */

public class HomeResultMsgHBean implements Serializable{

    private String time;
    private String serverTime;
    private int isOpen;
    private String game_image;
    private String game_name;
    private int game_code;
    private String round;
    private String result;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getGame_image() {
        return game_image;
    }

    public void setGame_image(String game_image) {
        this.game_image = game_image;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getGame_code() {
        return game_code;
    }

    public void setGame_code(int game_code) {
        this.game_code = game_code;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HomeResultMsgHBean() {
        super();
    }

    public HomeResultMsgHBean(String time, String serverTime, int isOpen, String game_image, String game_name, int game_code, String round, String result) {
        this.time = time;
        this.serverTime = serverTime;
        this.isOpen = isOpen;
        this.game_image = game_image;
        this.game_name = game_name;
        this.game_code = game_code;
        this.round = round;
        this.result = result;
    }

    @Override
    public String toString() {
        return "HomeResultMsgBean{" +
                "time='" + time + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", isOpen=" + isOpen +
                ", game_image='" + game_image + '\'' +
                ", game_name='" + game_name + '\'' +
                ", game_code=" + game_code +
                ", round='" + round + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
