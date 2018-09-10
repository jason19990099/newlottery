package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by XiaoXin on 2017/9/24.
 * 描述：首页的优惠信息
 */

public class PromotionModel extends BaseModel {


    /**
     * code : 1
     * msg : 2006
     * info : success
     * promotions : [{"image":"/images/movable/ali/gqzq.jpg","text":"礼惠国庆情满中秋","link":"https://wap.alcp66.com/#/activity1"},{"image":"/images/movable/ali/lxqd.jpg","text":"签到送豪礼","link":"https://wap.alcp66.com/#/activity2"},{"image":"/images/movable/ali/mrtz.jpg","text":"每日投注送大奖","link":"https://wap.alcp66.com/#/activity3"}]
     */

    public String info;
    public List<PromotionItem> promotions;

    public static class PromotionItem {
        public String image;
        public String text;
        public String link;
    }
}
