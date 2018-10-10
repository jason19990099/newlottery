package com.international.wtw.lottery.event;

public class OpenAndClosedEvent extends BaseEvent {
    //gameCode
    private String gameCode;
    //是否已封盘
    private boolean isClosed;

    private String expectNo;//正在投注的期号
    private boolean isClearSelect;  //是否清除已经选中的选项


    public OpenAndClosedEvent(String gameCode, String expectNo,boolean isClosed,boolean isClearSelect) {
        this.gameCode = gameCode;
        this.expectNo=expectNo;
        this.isClosed = isClosed;
        this.isClearSelect =isClearSelect;
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

    public boolean isClearSelect() {
        return isClearSelect;
    }

    public void setClearSelect(boolean clearSelect) {
        this.isClearSelect = clearSelect;
    }
}
