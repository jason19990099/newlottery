package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by wuya on 2017/6/15.
 */

public class BetResultBeanSucess implements Serializable {
    /**
     * item : 冠军『大』
     * countplay : 1
     * rate : 1.988
     * money : 1
     * re : true
     * id : 45308208
     */

    private String item;
    private int countplay;
    private String rate;
    private String money;
    private boolean re;
    private int id;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCountplay() {
        return countplay;
    }

    public void setCountplay(int countplay) {
        this.countplay = countplay;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isRe() {
        return re;
    }

    public void setRe(boolean re) {
        this.re = re;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
