package com.international.wtw.lottery.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 18Steven on 2017/9/22.轮播图的解析类
 */

public class LunbotuBean extends BaseModel implements Serializable {


    /**
     * code : 1
     * msg : 2006
     * info : success
     * rotations : ["http://androidapi.alcp66.com/images/ali/rotation/1.jpg","http://androidapi.alcp66.com/images/ali/rotation/2.jpg","http://androidapi.alcp66.com/images/ali/rotation/3.jpg"]
     * announcements : [{"time":1505997538,"content":"尊敬的会员：您好，平台收款民生银行【深圳市高翔科科技有限公司】账户停止收款，再次转入将无法充值，请悉知。","isPop":"1"},{"time":1502553844,"content":"尊敬的会员：您好，为了回馈广大会员长期以来对阿理彩票的支持，平台已上调双面盘赔率为1.998，上调定位胆赔率为9.98。其他赔率均保持不变。请各位新老会员相互转达积极参与体验，谢谢","isPop":"0"},{"time":1502282572,"content":"尊敬的会员:您好,由于近期部分QQ及QQ群被停用，给您带来不便，敬请谅解。为方便更好的为您提供服务，请添加客服QQ:513339990或QQ群:551669630，方便您随时咨询。若有变动会及时更新公告，敬请关注。","isPop":"0"},{"time":1502026822,"content":"尊敬的会员：您好，对于我司近期域名被劫持给您造成的困扰，我们深感抱歉。此问题现已解决，如发现打开平台网址后，跳转到非阿理彩票官网页面，请使用https://alcp88.com或https://alcp99.com等https防劫持线路进行游戏。给您带来不便，敬请谅解。","isPop":"0"},{"time":1499721823,"content":"尊敬的会员：为防止玩家恶意刷水平台禁止同一IP同期同种玩法累计投注超过总注单70%，两面盘不可同时投注大小/单双/龙虎。冠亚和最高只能够下注12个和值. 若发现违规且警告无效游戏平台将给予封号，并没收账户资金。感谢您的理解与配合。如有疑问请咨询客服！","isPop":"0"}]
     * pcJumpUrl : https://www.alcp66.com
     * customerSer:{"kefu":"https://tb.53kf.com/code/client/10147265/1"}}
     */

    private String pcJumpUrl;
    private List<String> rotations;
    private List<AnnouncementsBean> announcements;
    private ServiceBean customerSer;

    public String getPcJumpUrl() {
        return pcJumpUrl;
    }

    public void setPcJumpUrl(String pcJumpUrl) {
        this.pcJumpUrl = pcJumpUrl;
    }

    public List<String> getRotations() {
        return rotations;
    }

    public void setRotations(List<String> rotations) {
        this.rotations = rotations;
    }

    public List<AnnouncementsBean> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<AnnouncementsBean> announcements) {
        this.announcements = announcements;
    }

    public ServiceBean getCustomerSer() {
        return customerSer;
    }

    public void setCustomerSer(ServiceBean customerSer) {
        this.customerSer = customerSer;
    }

    public static class AnnouncementsBean implements Serializable {
        /**
         * time : 1505997538
         * content : 尊敬的会员：您好，平台收款民生银行【深圳市高翔科科技有限公司】账户停止收款，再次转入将无法充值，请悉知。
         * isPop : 1
         */

        private long time;
        private String content;
        private String isPop;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIsPop() {
            return isPop;
        }

        public void setIsPop(String isPop) {
            this.isPop = isPop;
        }
    }

    public static class ServiceBean implements Serializable {

        /**
         * kefu:https://tb.53kf.com/code/client/10147265/1
         */

        private String kefu;

        public String getKefu() {
            return kefu;
        }

        public void setKefu(String kefu) {
            this.kefu = kefu;
        }
    }
}
