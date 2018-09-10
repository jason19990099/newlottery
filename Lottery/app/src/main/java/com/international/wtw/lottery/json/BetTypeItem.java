package com.international.wtw.lottery.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by XiaoXin on 2017/9/19.
 * 描述：
 */
public class BetTypeItem implements MultiItemEntity {

    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_COMBO = 2;

    private int itemType;
    private int gameCode;
    private int typeCode;
    private String typeName;
    private int spanSize;
    private boolean selected;
    private String comboCode;
    private String odds;

    public BetTypeItem(int gameCode, int typeCode, String typeName, int spanSize) {
        this(gameCode, typeCode, typeName, spanSize, false);
    }

    public BetTypeItem(int gameCode, int typeCode, String typeName, int spanSize, boolean selected) {
        this.gameCode = gameCode;
        this.itemType = TYPE_NORMAL;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.spanSize = spanSize;
        this.selected = selected;
    }

    public BetTypeItem(int gameCode, int itemType, String comboCode, String typeName, String odds, int spanSize, boolean selected) {
        this.gameCode = gameCode;
        this.itemType = itemType;
        this.comboCode = comboCode;
        this.odds = odds;
        this.typeName = typeName;
        this.spanSize = spanSize;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getComboCode() {
        return comboCode;
    }

    public void setComboCode(String comboCode) {
        this.comboCode = comboCode;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public int getGameCode() {
        return this.gameCode;
    }

    public void setGameCode(int gameCode) {
        this.gameCode = gameCode;
    }
}
