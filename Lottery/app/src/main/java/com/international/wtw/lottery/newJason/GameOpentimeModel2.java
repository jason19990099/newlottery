package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class GameOpentimeModel2 extends BaseModel {


    /**
     * timestamp : 1547792158
     * data : [{"GameCode":"bjk3","GameName":"北京快3","ExpectNo":"","ExpectNoNext":"131471","StartTime":1547791800,"Code":"","CloseTime":1547792280,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"bjscpk10","GameName":"北京赛车(PK10)","ExpectNo":"","ExpectNoNext":"726411","StartTime":1547791950,"Code":"","CloseTime":1547792220,"OpenTime":1547792250,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"cqssc","GameName":"重庆时时彩","ExpectNo":"","ExpectNoNext":"20190118050","StartTime":1547791800,"Code":"","CloseTime":1547792355,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"cqxync","GameName":"重庆幸运农场","ExpectNo":"","ExpectNoNext":"190118040","StartTime":1547791940,"Code":"","CloseTime":1547792420,"OpenTime":1547792540,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"gd11x5","GameName":"广东11选5","ExpectNo":"","ExpectNoNext":"19011832","StartTime":1547791800,"Code":"","CloseTime":1547792280,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"gdklsf","GameName":"广东快乐十分","ExpectNo":"","ExpectNoNext":"2019011832","StartTime":1547791800,"Code":"","CloseTime":1547792280,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"gsk3","GameName":"甘肃快3","ExpectNo":"","ExpectNoNext":"190118026","StartTime":1547791800,"Code":"","CloseTime":1547792280,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"gxk3","GameName":"广西快3","ExpectNo":"","ExpectNoNext":"190118029","StartTime":1547791620,"Code":"","CloseTime":1547792100,"OpenTime":1547792220,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"gzk3","GameName":"贵州快3","ExpectNo":"","ExpectNoNext":"190118032","StartTime":1547791800,"Code":"","CloseTime":1547792280,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"jsk3","GameName":"江苏快3","ExpectNo":"","ExpectNoNext":"190118035","StartTime":1547791680,"Code":"","CloseTime":1547792170,"OpenTime":1547792280,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"pcdd","GameName":"PC蛋蛋","ExpectNo":"","ExpectNoNext":"932389","StartTime":1547792100,"Code":"","CloseTime":1547792360,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"shk3","GameName":"上海快3","ExpectNo":"","ExpectNoNext":"190118033","StartTime":1547791680,"Code":"","CloseTime":1547792160,"OpenTime":1547792280,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"tjssc","GameName":"天津时时彩","ExpectNo":"","ExpectNoNext":"20190118033","StartTime":1547792100,"Code":"","CloseTime":1547792580,"OpenTime":1547792700,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"xjssc","GameName":"新疆时时彩","ExpectNo":"","ExpectNoNext":"2019011826","StartTime":1547791800,"Code":"","CloseTime":1547792280,"OpenTime":1547792400,"IsToday":false,"Sort":0,"ServerTime":1547792160},{"GameCode":"xydd","GameName":"幸运蛋蛋","ExpectNo":"","ExpectNoNext":"190118076","StartTime":1547791980,"Code":"","CloseTime":1547792220,"OpenTime":1547792280,"IsToday":false,"Sort":0,"ServerTime":1547792160}]
     * clientip : 203.177.163.198
     */

    private int timestamp;
    private String clientip;
    private List<DataBean> data;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * GameCode : bjk3
         * GameName : 北京快3
         * ExpectNo :
         * ExpectNoNext : 131471
         * StartTime : 1547791800
         * Code :
         * CloseTime : 1547792280
         * OpenTime : 1547792400
         * IsToday : false
         * Sort : 0
         * ServerTime : 1547792160
         */

        private String GameCode;
        private String GameName;
        private String ExpectNo;
        private String ExpectNoNext;
        private int StartTime;
        private String Code;
        private int CloseTime;
        private int OpenTime;
        private boolean IsToday;
        private int Sort;
        private int ServerTime;

        public String getGameCode() {
            return GameCode;
        }

        public void setGameCode(String GameCode) {
            this.GameCode = GameCode;
        }

        public String getGameName() {
            return GameName;
        }

        public void setGameName(String GameName) {
            this.GameName = GameName;
        }

        public String getExpectNo() {
            return ExpectNo;
        }

        public void setExpectNo(String ExpectNo) {
            this.ExpectNo = ExpectNo;
        }

        public String getExpectNoNext() {
            return ExpectNoNext;
        }

        public void setExpectNoNext(String ExpectNoNext) {
            this.ExpectNoNext = ExpectNoNext;
        }

        public int getStartTime() {
            return StartTime;
        }

        public void setStartTime(int StartTime) {
            this.StartTime = StartTime;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public int getCloseTime() {
            return CloseTime;
        }

        public void setCloseTime(int CloseTime) {
            this.CloseTime = CloseTime;
        }

        public int getOpenTime() {
            return OpenTime;
        }

        public void setOpenTime(int OpenTime) {
            this.OpenTime = OpenTime;
        }

        public boolean isIsToday() {
            return IsToday;
        }

        public void setIsToday(boolean IsToday) {
            this.IsToday = IsToday;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public int getServerTime() {
            return ServerTime;
        }

        public void setServerTime(int ServerTime) {
            this.ServerTime = ServerTime;
        }
    }
}
