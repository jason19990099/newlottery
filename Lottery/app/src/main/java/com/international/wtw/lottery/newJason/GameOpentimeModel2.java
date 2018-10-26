package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class GameOpentimeModel2 extends BaseModel {

    /**
     * data : [{"GameCode":"msssc","ExpectNo":"","ExpectNoNext":"181026245","StartTime":1540528425,"Code":"","CloseTime":1540528485,"OpenTime":1540528500,"IsToday":false,"Sort":0,"ServerTime":1540528446},{"GameCode":"msft","ExpectNo":"","ExpectNoNext":"181026245","StartTime":1540528395,"Code":"","CloseTime":1540528455,"OpenTime":1540528470,"IsToday":false,"Sort":0,"ServerTime":1540528446},{"GameCode":"bjscpk10","ExpectNo":"","ExpectNoNext":"711355","StartTime":1540528350,"Code":"","CloseTime":1540528620,"OpenTime":1540528650,"IsToday":false,"Sort":0,"ServerTime":1540528446},{"GameCode":"mssc","ExpectNo":"181026244","ExpectNoNext":"181026245","StartTime":1540528425,"Code":"4,10,1,3,7,2,5,6,8,9","CloseTime":1540528485,"OpenTime":1540528500,"IsToday":false,"Sort":0,"ServerTime":1540528446}]
     * timestamp : 1540528445
     */

    private int timestamp;
    private List<DataBean> data;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * GameCode : msssc
         * ExpectNo :
         * ExpectNoNext : 181026245
         * StartTime : 1540528425
         * Code :
         * CloseTime : 1540528485
         * OpenTime : 1540528500
         * IsToday : false
         * Sort : 0
         * ServerTime : 1540528446
         */

        private String GameCode;
        private String ExpectNo;
        private String ExpectNoNext;
        private long StartTime;
        private String Code;
        private long CloseTime;
        private long OpenTime;
        private boolean IsToday;
        private int Sort;
        private long ServerTime;

        public String getGameCode() {
            return GameCode;
        }

        public void setGameCode(String gameCode) {
            GameCode = gameCode;
        }

        public String getExpectNo() {
            return ExpectNo;
        }

        public void setExpectNo(String expectNo) {
            ExpectNo = expectNo;
        }

        public String getExpectNoNext() {
            return ExpectNoNext;
        }

        public void setExpectNoNext(String expectNoNext) {
            ExpectNoNext = expectNoNext;
        }

        public long getStartTime() {
            return StartTime;
        }

        public void setStartTime(long startTime) {
            StartTime = startTime;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public long getCloseTime() {
            return CloseTime;
        }

        public void setCloseTime(long closeTime) {
            CloseTime = closeTime;
        }

        public long getOpenTime() {
            return OpenTime;
        }

        public void setOpenTime(long openTime) {
            OpenTime = openTime;
        }

        public boolean isToday() {
            return IsToday;
        }

        public void setToday(boolean today) {
            IsToday = today;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int sort) {
            Sort = sort;
        }

        public long getServerTime() {
            return ServerTime;
        }

        public void setServerTime(long serverTime) {
            ServerTime = serverTime;
        }
    }
}
