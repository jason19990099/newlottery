package com.international.wtw.lottery.event;

/**
 * Created by XiaoXin on 2017/9/20.
 * 描述：封盘事件
 */

public class BetClosedEvent extends BaseEvent {

    //gameCode
    public int gameCode;
    //是否已封盘
    public boolean isClosed;

    public BetClosedEvent(int gameCode, boolean isClosed) {
        this.gameCode = gameCode;
        this.isClosed = isClosed;
    }
}
