package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/4.
 */

public class AGChangeBean extends BaseModel implements Serializable{

    private String amount ;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
