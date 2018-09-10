package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/7/27.
 */

public class HomeResultBean extends BaseModel{

    private List<HomeResultMsgBean> timeStamp;

    public List<HomeResultMsgBean> getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(List<HomeResultMsgBean> timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HomeResultBean() {
        super();
    }

    public HomeResultBean(List<HomeResultMsgBean> timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "HomeResultBean{" +
                "timeStamp=" + timeStamp +
                '}';
    }
}
