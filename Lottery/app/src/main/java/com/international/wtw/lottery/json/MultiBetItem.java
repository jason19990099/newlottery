package com.international.wtw.lottery.json;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by XiaoXin on 2017/9/20.
 * 描述：下注选项
 */
public class MultiBetItem implements MultiItemEntity, Comparable<MultiBetItem> {
    public static final int TITLE = 1;
    public static final int CONTENT_TEXT = 2;
    public static final int CONTENT_NUM = 3;
    public static final int CONTENT_LIAN = 4;
    public static final int CONTENT_MULTI_NUM = 5;

    private int gameCode;

    private int typeCode;

    private int itemType;
    private int spanSize;
    private boolean isSelected;
    private String typeName;
    private int number;
    private OddsItem betItem;

    public MultiBetItem(int gameCode, int typeCode, int itemType, int spanSize, String typeName) {
        this.gameCode = gameCode;
        this.typeCode = typeCode;
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.typeName = typeName;
    }

    public MultiBetItem(int gameCode, int typeCode, int itemType, int spanSize, String typeName, int number) {
        this.gameCode = gameCode;
        this.typeCode = typeCode;
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.typeName = typeName;
        this.number = number;
    }

    public MultiBetItem(int gameCode, int typeCode, int itemType, int spanSize, String typeName, OddsItem betItem) {
        this.gameCode = gameCode;
        this.typeCode = typeCode;
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.typeName = typeName;
        this.betItem = betItem;
    }


    public int getItemType() {
        return itemType;
    }

    @Override
    public int compareTo(@NonNull MultiBetItem o) {
        if (betItem != null) {
            String[] split = betItem.key.split("_");
            String[] split1 = o.betItem.key.split("_");
            if (split.length == 2 && split1.length == 2) {
                int a = Integer.valueOf(split[split.length - 1]);
                int b = Integer.valueOf(split1[split1.length - 1]);
                return a - b;
            } else if (split.length == 2 && split1.length == 3) {
                int a = Integer.valueOf(split[split.length - 1]);
                int b = Integer.valueOf(split1[split1.length - 2]);
                if (a == b) {
                    return -1;
                }
                return a - b;
            } else if (split.length == 3 && split1.length == 2) {
                int a = Integer.valueOf(split[split.length - 2]);
                int b = Integer.valueOf(split1[split1.length - 1]);
                if (a == b) {
                    return 1;
                }
                return a - b;
            } else {
                int a = Integer.valueOf(split[split.length - 2]);
                int b = Integer.valueOf(split1[split1.length - 2]);
                if (a == b) {
                    a = Integer.valueOf(split[split.length - 1]);
                    b = Integer.valueOf(split1[split1.length - 1]);
                }
                return a - b;
            }
        } else {
            return this.number - o.number;
        }
    }

    @Override
    public String toString() {
        return "MultiBetItem{" +
                ", gameCode=" + gameCode +
                ", typeCode=" + typeCode +
                ", itemType=" + itemType +
                ", spanSize=" + spanSize +
                ", isSelected=" + isSelected +
                ", typeName='" + typeName + '\'' +
                ", betItem=" + betItem +
                '}';
    }
    public int getGameCode() {
        return this.gameCode;
    }

    public void setGameCode(int gameCode) {
        this.gameCode = gameCode;
    }

    public int getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getSpanSize() {
        return this.spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public OddsItem getBetItem() {
        return this.betItem;
    }

    public void setBetItem(OddsItem betItem) {
        this.betItem = betItem;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
