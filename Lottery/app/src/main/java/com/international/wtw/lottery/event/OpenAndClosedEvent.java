package com.international.wtw.lottery.event;

public class OpenAndClosedEvent extends BaseEvent {
    //gameCode
    private String gameCode;
    //是否已封盘
    private boolean isClosed;

    private String expectNo;//正在投注的期号

    public OpenAndClosedEvent(String gameCode, String expectNo,boolean isClosed) {
        this.gameCode = gameCode;
        this.expectNo=expectNo;
        this.isClosed = isClosed;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getExpectNo() {
        return expectNo;
    }

    public void setExpectNo(String expectNo) {
        this.expectNo = expectNo;
    }
}
