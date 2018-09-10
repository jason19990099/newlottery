package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by A Bin on 2017/7/30.
 * 右侧菜单子项类
 */

public class RightMenu implements Serializable {
    int imageId;
    String describe;

    public RightMenu(int imageId, String describe) {
        this.imageId = imageId;
        this.describe = describe;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
