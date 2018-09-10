package com.international.wtw.lottery.json;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Quick3Odds extends BaseModel {

    private List<OddsModel> three_size;
    private List<OddsModel> dice;
    private List<OddsModel> point;
    @SerializedName("long")
    private List<OddsModel> longX;
    @SerializedName("short")
    private List<OddsModel> shortX;

    public List<OddsModel> getThree_size() {
        return three_size;
    }

    public void setThree_size(List<OddsModel> three_size) {
        this.three_size = three_size;
    }

    public List<OddsModel> getDice() {
        return dice;
    }

    public void setDice(List<OddsModel> dice) {
        this.dice = dice;
    }

    public List<OddsModel> getPoint() {
        return point;
    }

    public void setPoint(List<OddsModel> point) {
        this.point = point;
    }

    public List<OddsModel> getLongX() {
        return longX;
    }

    public void setLongX(List<OddsModel> longX) {
        this.longX = longX;
    }

    public List<OddsModel> getShortX() {
        return shortX;
    }

    public void setShortX(List<OddsModel> shortX) {
        this.shortX = shortX;
    }
}
