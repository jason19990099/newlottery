package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class WithdrawRecordModel extends BaseModel {

    /**
     * data : [{"Id":"181024152859890002","DictionaryId":170,"DictionaryName":"后台扣钱","UserId":"181005161602202088","Username":"it0010","TrueName":"小胖子","Amount":200,"BankId":0,"BankInfo":"","PageUrl":"http://22660.vip/User/Withdraw","BrowserInfo":"okhttp/3.8.0","WithdrawStatus":4,"WithdrawStatusName":"通过","UpdateAdminName":"后台扣钱","Description":null,"User":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":1540366139,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":"181024152005399076","DictionaryId":170,"DictionaryName":"后台扣钱","UserId":"181005161602202088","Username":"it0010","TrueName":"小胖子","Amount":265,"BankId":0,"BankInfo":"","PageUrl":"http://22660.vip/User/Withdraw","BrowserInfo":"okhttp/3.8.0","WithdrawStatus":4,"WithdrawStatusName":"通过","UpdateAdminName":"后台扣钱","Description":null,"User":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":1540365605,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":"181024151754291072","DictionaryId":170,"DictionaryName":"后台扣钱","UserId":"181005161602202088","Username":"it0010","TrueName":"小胖子","Amount":200,"BankId":0,"BankInfo":"","PageUrl":"http://22660.vip/User/Withdraw","BrowserInfo":"okhttp/3.8.0","WithdrawStatus":4,"WithdrawStatusName":"通过","UpdateAdminName":"后台扣钱","Description":null,"User":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":1540365474,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":"181024151425970070","DictionaryId":170,"DictionaryName":"后台扣钱","UserId":"181005161602202088","Username":"it0010","TrueName":"小胖子","Amount":2003,"BankId":0,"BankInfo":"","PageUrl":"http://22660.vip/User/Withdraw","BrowserInfo":"okhttp/3.8.0","WithdrawStatus":4,"WithdrawStatusName":"通过","UpdateAdminName":"后台扣钱","Description":null,"User":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":1540365265,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":"181024151412555068","DictionaryId":170,"DictionaryName":"后台扣钱","UserId":"181005161602202088","Username":"it0010","TrueName":"小胖子","Amount":300,"BankId":0,"BankInfo":"","PageUrl":"http://22660.vip/User/Withdraw","BrowserInfo":"okhttp/3.8.0","WithdrawStatus":4,"WithdrawStatusName":"通过","UpdateAdminName":"后台扣钱","Description":null,"User":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":1540365252,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":"181024151403690066","DictionaryId":170,"DictionaryName":"后台扣钱","UserId":"181005161602202088","Username":"it0010","TrueName":"小胖子","Amount":200,"BankId":0,"BankInfo":"","PageUrl":"http://22660.vip/User/Withdraw","BrowserInfo":"okhttp/3.8.0","WithdrawStatus":4,"WithdrawStatusName":"通过","UpdateAdminName":"后台扣钱","Description":null,"User":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":1540365243,"UpdateBy":"0","UpdateTime":-2209017600}]
     * timestamp  : 1540366370
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
         * Id : 181024152859890002
         * DictionaryId : 170
         * DictionaryName : 后台扣钱
         * UserId : 181005161602202088
         * Username : it0010
         * TrueName : 小胖子
         * Amount : 200
         * BankId : 0
         * BankInfo :
         * PageUrl : http://22660.vip/User/Withdraw
         * BrowserInfo : okhttp/3.8.0
         * WithdrawStatus : 4
         * WithdrawStatusName : 通过
         * UpdateAdminName : 后台扣钱
         * Description : null
         * User : null
         * pageIndex : 1
         * pageSize : 10
         * pageSort : null
         * minAmount : 0
         * maxAmount : 0
         * beginTime : -2209017600
         * endTime : -2209017600
         * Sort : 0
         * IsDisable : false
         * CreateBy : 0
         * CreateTime : 1540366139
         * UpdateBy : 0
         * UpdateTime : -2209017600
         */

        private String Id;
        private int DictionaryId;
        private String DictionaryName;
        private String UserId;
        private String Username;
        private String TrueName;
        private String Amount;
        private int BankId;
        private String BankInfo;
        private String PageUrl;
        private String BrowserInfo;
        private int WithdrawStatus;
        private String WithdrawStatusName;
        private String UpdateAdminName;
        private Object Description;
        private Object User;
        private int pageIndex;
        private int pageSize;
        private Object pageSort;
        private int minAmount;
        private int maxAmount;
        private long beginTime;
        private long endTime;
        private int Sort;
        private boolean IsDisable;
        private String CreateBy;
        private int CreateTime;
        private String UpdateBy;
        private long UpdateTime;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public int getDictionaryId() {
            return DictionaryId;
        }

        public void setDictionaryId(int DictionaryId) {
            this.DictionaryId = DictionaryId;
        }

        public String getDictionaryName() {
            return DictionaryName;
        }

        public void setDictionaryName(String DictionaryName) {
            this.DictionaryName = DictionaryName;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String Username) {
            this.Username = Username;
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String TrueName) {
            this.TrueName = TrueName;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public int getBankId() {
            return BankId;
        }

        public void setBankId(int BankId) {
            this.BankId = BankId;
        }

        public String getBankInfo() {
            return BankInfo;
        }

        public void setBankInfo(String BankInfo) {
            this.BankInfo = BankInfo;
        }

        public String getPageUrl() {
            return PageUrl;
        }

        public void setPageUrl(String PageUrl) {
            this.PageUrl = PageUrl;
        }

        public String getBrowserInfo() {
            return BrowserInfo;
        }

        public void setBrowserInfo(String BrowserInfo) {
            this.BrowserInfo = BrowserInfo;
        }

        public int getWithdrawStatus() {
            return WithdrawStatus;
        }

        public void setWithdrawStatus(int WithdrawStatus) {
            this.WithdrawStatus = WithdrawStatus;
        }

        public String getWithdrawStatusName() {
            return WithdrawStatusName;
        }

        public void setWithdrawStatusName(String WithdrawStatusName) {
            this.WithdrawStatusName = WithdrawStatusName;
        }

        public String getUpdateAdminName() {
            return UpdateAdminName;
        }

        public void setUpdateAdminName(String UpdateAdminName) {
            this.UpdateAdminName = UpdateAdminName;
        }

        public Object getDescription() {
            return Description;
        }

        public void setDescription(Object Description) {
            this.Description = Description;
        }

        public Object getUser() {
            return User;
        }

        public void setUser(Object User) {
            this.User = User;
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

        public int getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(int minAmount) {
            this.minAmount = minAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(int maxAmount) {
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

        public int getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(int CreateTime) {
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
