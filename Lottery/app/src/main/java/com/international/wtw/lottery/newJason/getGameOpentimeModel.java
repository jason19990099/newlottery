package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

public class getGameOpentimeModel extends BaseModel {


    /**
     * data : {"GameCode":"bjscpk10","ExpectNo":"706925","ExpectNoNext":"706926","StartTime":1538382150,"Code":"05,03,07,02,06,09,04,08,10,01","CloseTime":1538382420,"OpenTime":1538382450,"IsToday":false,"Sort":0,"ServerTime":1538382397}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * GameCode : bjscpk10
         * ExpectNo : 706925
         * ExpectNoNext : 706926
         * StartTime : 1538382150
         * Code : 05,03,07,02,06,09,04,08,10,01
         * CloseTime : 1538382420
         * OpenTime : 1538382450
         * IsToday : false
         * Sort : 0
         * ServerTime : 1538382397
         */

        private String GameCode;
        private String ExpectNo;
        private String ExpectNoNext;
        private String StartTime;
        private String Code;
        private String CloseTime;
        private String OpenTime;
        private boolean IsToday;
        private int Sort;
        private String ServerTime;

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

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getCloseTime() {
            return CloseTime;
        }

        public void setCloseTime(String closeTime) {
            CloseTime = closeTime;
        }

        public String getOpenTime() {
            return OpenTime;
        }

        public void setOpenTime(String openTime) {
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

        public String getServerTime() {
            return ServerTime;
        }

        public void setServerTime(String serverTime) {
            ServerTime = serverTime;
        }
    }
}
