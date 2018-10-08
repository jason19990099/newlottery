package com.international.wtw.lottery.event;

public class OpenAndClosedEvent extends BaseEvent {
    //gameCode
    public String gameCode;
    //是否已封盘
    public boolean isClosed;

    public OpenAndClosedEvent(String gameCode, boolean isClosed) {
        this.gameCode = gameCode;
        this.isClosed = isClosed;
    }
}
