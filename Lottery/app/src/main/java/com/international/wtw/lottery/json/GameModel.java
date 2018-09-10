package com.international.wtw.lottery.json;

/**
 * 游戏Model
 */

public class GameModel {

    public int gameCode;
    public String gameName;
    public int logoResId;

    public GameModel(int gameCode, String gameName, int logoResId) {
        this.gameCode = gameCode;
        this.gameName = gameName;
        this.logoResId = logoResId;
    }
}
