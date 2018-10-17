package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class WeekdateModel extends BaseModel {

    /**
     * data : ["2018-10-17","2018-10-16","2018-10-15","2018-10-14","2018-10-13","2018-10-12","2018-10-11"]
     * timestamp : 1539759355
     */

    private int timestamp;
    private List<String> data;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
