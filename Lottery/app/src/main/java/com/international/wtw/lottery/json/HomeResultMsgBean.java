package com.international.wtw.lottery.json;

import android.support.annotation.NonNull;

import com.international.wtw.lottery.base.Constants;

import java.io.Serializable;

/**
 * Created by XIAOYAN on 2017/7/27.
 */

public class HomeResultMsgBean implements Serializable, Comparable<HomeResultMsgBean> {

    private String time;
    private Long serverTime;
    private String isOpen;
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

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
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

    private int getOrder() {
        int order = 0;
        switch (game_code) {
            case Constants.LOTTERY_TYPE.PJ_PK_10://北京赛车
                order = 0;
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY://重庆时时彩
                order = 1;
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                order = 2;
                break;
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                order = 3;
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                order = 4;
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
                order = 5;
                break;
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
                order = 6;
                break;
            case Constants.LOTTERY_TYPE.JS_QUICK_3:
                order = 7;//江苏快3
                break;
            case Constants.LOTTERY_TYPE.ROME_LOTTERY:
                order = 8;//罗马时时彩
                break;
            case Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT:
                order = 9;//威尼斯赛艇
                break;
            case Constants.LOTTERY_TYPE.SPEED_CAR:
                order = 10;//极速赛车
                break;
            case Constants.LOTTERY_TYPE.SPEED_SSC:
                order = 11;//极速时时彩
                break;
            case Constants.LOTTERY_TYPE.HORSE_88:
                order = 12;//88赛马
                break;
            case Constants.LOTTERY_TYPE.SPEED_MARK_SIX:
                order = 13;//极速六合彩
                break;
        }
        return order;
    }

    public HomeResultMsgBean() {
        super();
    }

    public HomeResultMsgBean(String time, Long serverTime, String isOpen, String game_image, String game_name, int game_code, String round, String result) {
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
                ", serverTime=" + serverTime +
                ", isOpen='" + isOpen + '\'' +
                ", game_image='" + game_image + '\'' +
                ", game_name='" + game_name + '\'' +
                ", game_code=" + game_code +
                ", round='" + round + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull HomeResultMsgBean o) {
        return this.getOrder() - o.getOrder();
    }
}
