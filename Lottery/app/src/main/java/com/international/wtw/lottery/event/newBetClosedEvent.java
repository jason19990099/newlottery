package com.international.wtw.lottery.event;

/**
 * Created by XiaoXin on 2017/9/20.
 * 描述：封盘事件
 */

public class newBetClosedEvent extends BaseEvent {

    //gameCode
    public String gameCode;
    //是否已封盘
    public boolean isClosed;

    public newBetClosedEvent(String gameCode, boolean isClosed) {
        this.gameCode = gameCode;
        this.isClosed = isClosed;
    }
}
