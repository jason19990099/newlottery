package com.international.wtw.lottery.listener;

/**
 * Created by 18Steven on 2017/7/12. 彩票通知开盘 封盘的接口
 */

public interface StateInterface {
    void open(Boolean b);
    void close(Boolean b);
}
