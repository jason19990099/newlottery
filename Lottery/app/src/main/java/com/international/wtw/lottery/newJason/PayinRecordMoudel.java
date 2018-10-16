package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class PayinRecordMoudel extends BaseModel {
    private List<DataBean> data;
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Id : 181009154739832000
         * UserId : 181005161602202088
         * Username : it0010
         * Amount : 1000000
         * PaymentMethodId : 0
         * PaymentMethodName : null
         * PaymentInfo : null
         * ReceiveMethod : 0
         * ReceiveMethodName : null
         * ReceiveInfo : null
         * PageUrl : http://10.7.0.4:8081/User/UpdateAmount
         * BrowserInfo : Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36
         * RechargeStatus : 4
         * RechargeStatusName : 充值成功
         * DictionaryId : 158
         * DictionaryName : 后台加钱
         * UpdateAdminName : admin
         * Description : 代充, 一百万 ~
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
         * CreateTime : 1539071259
         * UpdateBy : 0
         * UpdateTime : -2209017600
         */

        private String Id;
        private String UserId;
        private String Username;
        private int Amount;
        private int PaymentMethodId;
        private Object PaymentMethodName;
        private Object PaymentInfo;
        private int ReceiveMethod;
        private Object ReceiveMethodName;
        private Object ReceiveInfo;
        private String PageUrl;
        private String BrowserInfo;
        private int RechargeStatus;
        private String RechargeStatusName;
        private int DictionaryId;
        private String DictionaryName;
        private String UpdateAdminName;
        private String Description;
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

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public int getPaymentMethodId() {
            return PaymentMethodId;
        }

        public void setPaymentMethodId(int PaymentMethodId) {
            this.PaymentMethodId = PaymentMethodId;
        }

        public Object getPaymentMethodName() {
            return PaymentMethodName;
        }

        public void setPaymentMethodName(Object PaymentMethodName) {
            this.PaymentMethodName = PaymentMethodName;
        }

        public Object getPaymentInfo() {
            return PaymentInfo;
        }

        public void setPaymentInfo(Object PaymentInfo) {
            this.PaymentInfo = PaymentInfo;
        }

        public int getReceiveMethod() {
            return ReceiveMethod;
        }

        public void setReceiveMethod(int ReceiveMethod) {
            this.ReceiveMethod = ReceiveMethod;
        }

        public Object getReceiveMethodName() {
            return ReceiveMethodName;
        }

        public void setReceiveMethodName(Object ReceiveMethodName) {
            this.ReceiveMethodName = ReceiveMethodName;
        }

        public Object getReceiveInfo() {
            return ReceiveInfo;
        }

        public void setReceiveInfo(Object ReceiveInfo) {
            this.ReceiveInfo = ReceiveInfo;
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

        public int getRechargeStatus() {
            return RechargeStatus;
        }

        public void setRechargeStatus(int RechargeStatus) {
            this.RechargeStatus = RechargeStatus;
        }

        public String getRechargeStatusName() {
            return RechargeStatusName;
        }

        public void setRechargeStatusName(String RechargeStatusName) {
            this.RechargeStatusName = RechargeStatusName;
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

        public String getUpdateAdminName() {
            return UpdateAdminName;
        }

        public void setUpdateAdminName(String UpdateAdminName) {
            this.UpdateAdminName = UpdateAdminName;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
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
