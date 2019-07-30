package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class BankcardsModel extends BaseModel {


    /**
     * timestamp : 1548318006
     * data : [{"IsDelete":false,"Id":1001,"Name":"银行卡提现","MinValue":10,"MaxValue":10000,"UserBank":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"IsDelete":false,"Id":1002,"Name":"微信提现","MinValue":10,"MaxValue":100,"UserBank":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"IsDelete":false,"Id":1003,"Name":"支付宝提现","MinValue":10,"MaxValue":100,"UserBank":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600}]
     * clientip : 10.1.246.46
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
         * IsDelete : false
         * Id : 1001
         * Name : 银行卡提现
         * MinValue : 10.0
         * MaxValue : 10000.0
         * UserBank : null
         * pageIndex : 1
         * pageSize : 10
         * pageSort : null
         * minAmount : 0.0
         * maxAmount : 0.0
         * beginTime : -2209017600
         * endTime : -2209017600
         * Sort : 0
         * IsDisable : false
         * CreateBy : 0
         * CreateTime : -2209017600
         * UpdateBy : 0
         * UpdateTime : -2209017600
         */

        private boolean IsDelete;
        private int Id;
        private String Name;
        private double MinValue;
        private double MaxValue;
        private Object UserBank;
        private int pageIndex;
        private int pageSize;
        private Object pageSort;
        private double minAmount;
        private double maxAmount;
        private long beginTime;
        private long endTime;
        private int Sort;
        private boolean IsDisable;
        private String CreateBy;
        private long CreateTime;
        private String UpdateBy;
        private long UpdateTime;

        public boolean isIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(boolean IsDelete) {
            this.IsDelete = IsDelete;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public double getMinValue() {
            return MinValue;
        }

        public void setMinValue(double MinValue) {
            this.MinValue = MinValue;
        }

        public double getMaxValue() {
            return MaxValue;
        }

        public void setMaxValue(double MaxValue) {
            this.MaxValue = MaxValue;
        }

        public Object getUserBank() {
            return UserBank;
        }

        public void setUserBank(Object UserBank) {
            this.UserBank = UserBank;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public Object getPageSort() {
            return pageSort;
        }

        public void setPageSort(Object pageSort) {
            this.pageSort = pageSort;
        }

        public double getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(double minAmount) {
            this.minAmount = minAmount;
        }

        public double getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(double maxAmount) {
            this.maxAmount = maxAmount;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public boolean isIsDisable() {
            return IsDisable;
        }

        public void setIsDisable(boolean IsDisable) {
            this.IsDisable = IsDisable;
        }

        public String getCreateBy() {
            return CreateBy;
        }

        public void setCreateBy(String CreateBy) {
            this.CreateBy = CreateBy;
        }

        public long getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(long CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateBy() {
            return UpdateBy;
        }

        public void setUpdateBy(String UpdateBy) {
            this.UpdateBy = UpdateBy;
        }

        public long getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(long UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
