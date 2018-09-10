package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/10/6.
 */

public class PreferentialBean extends BaseModel{

    private List<PreferentialProBean> promotions;

    public List<PreferentialProBean> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PreferentialProBean> promotions) {
        this.promotions = promotions;
    }

    public PreferentialBean() {
        super();
    }

    public PreferentialBean(List<PreferentialProBean> promotions) {
        this.promotions = promotions;
    }

    @Override
    public String toString() {
        return "PreferentialBean{" +
                "promotions=" + promotions +
                '}';
    }
}
