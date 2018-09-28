package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

public class getGameOpentime extends BaseModel {


    /**
     * data : {"GameCode":"bjscpk10","ExpectNo":"706386","ExpectNoNext":"706387","StartTime":"2018/9/28 16:12:30","Code":"03,09,02,06,04,07,01,05,08,10","CloseTime":"2018/9/28 16:17:00","OpenTime":"2018/9/28 16:17:30","IsToday":false,"Sort":0,"ServerTime":"2018-09-28 16:15:14"}
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
         * ExpectNo : 706386
         * ExpectNoNext : 706387
         * StartTime : 2018/9/28 16:12:30
         * Code : 03,09,02,06,04,07,01,05,08,10
         * CloseTime : 2018/9/28 16:17:00
         * OpenTime : 2018/9/28 16:17:30
         * IsToday : false
         * Sort : 0
         * ServerTime : 2018-09-28 16:15:14
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

        public void setGameCode(String GameCode) {
            this.GameCode = GameCode;
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

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getCloseTime() {
            return CloseTime;
        }

        public void setCloseTime(String CloseTime) {
            this.CloseTime = CloseTime;
        }

        public String getOpenTime() {
            return OpenTime;
        }

        public void setOpenTime(String OpenTime) {
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

        public String getServerTime() {
            return ServerTime;
        }

        public void setServerTime(String ServerTime) {
            this.ServerTime = ServerTime;
        }
    }
}
