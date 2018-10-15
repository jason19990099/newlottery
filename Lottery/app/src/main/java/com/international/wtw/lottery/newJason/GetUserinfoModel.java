package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

public class GetUserinfoModel extends BaseModel {

    /**
     * data : {"Id":"180917160400828906","Name":"it0001","SourceType":0,"SourceTypeName":null,"Ip":"10.1.246.46","IpInfo":"对方和您在同一内部网","AgentIp":"10.1.246.46","AgentIpInfo":"对方和您在同一内部网","BrowserInfo":"okhttp/3.8.0","Domain":"10.7.0.2","LoginTime":"/Date(1537248634077)/","LastActionPageUrl":"","LastActionTime":"/Date(1537248634077)/","UserStatus":1,"UserStatusName":"正常","UserOnlineType":1,"UserOnlineTypeName":"用户","Amount":0,"Token":"b65903e783444a2991bf160f66fe10c6","pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":"/Date(-2209017600000)/","endTime":"/Date(-2209017600000)/","Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":"/Date(-2209017600000)/","UpdateBy":"0","UpdateTime":"/Date(-2209017600000)/"}
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
         * Id : 180917160400828906
         * Name : it0001
         * SourceType : 0
         * SourceTypeName : null
         * Ip : 10.1.246.46
         * IpInfo : 对方和您在同一内部网
         * AgentIp : 10.1.246.46
         * AgentIpInfo : 对方和您在同一内部网
         * BrowserInfo : okhttp/3.8.0
         * Domain : 10.7.0.2
         * LoginTime : /Date(1537248634077)/
         * LastActionPageUrl :
         * LastActionTime : /Date(1537248634077)/
         * UserStatus : 1
         * UserStatusName : 正常
         * UserOnlineType : 1
         * UserOnlineTypeName : 用户
         * Amount : 0.0
         * Token : b65903e783444a2991bf160f66fe10c6
         * pageIndex : 1
         * pageSize : 10
         * pageSort : null
         * minAmount : 0.0
         * maxAmount : 0.0
         * beginTime : /Date(-2209017600000)/
         * endTime : /Date(-2209017600000)/
         * Sort : 0
         * IsDisable : false
         * CreateBy : 0
         * CreateTime : /Date(-2209017600000)/
         * UpdateBy : 0
         * UpdateTime : /Date(-2209017600000)/
         */

        private String Id;
        private String Name;
        private int SourceType;
        private Object SourceTypeName;
        private String Ip;
        private String IpInfo;
        private String AgentIp;
        private String AgentIpInfo;
        private String BrowserInfo;
        private String Domain;
        private String LoginTime;
        private String LastActionPageUrl;
        private String LastActionTime;
        private int UserStatus;
        private String UserStatusName;
        private int UserOnlineType;
        private String UserOnlineTypeName;
        private double Amount;
        private String Token;
        private int pageIndex;
        private int pageSize;
        private Object pageSort;
        private double minAmount;
        private double maxAmount;
        private String beginTime;
        private String endTime;
        private int Sort;
        private boolean IsDisable;
        private String CreateBy;
        private String CreateTime;
        private String UpdateBy;
        private String UpdateTime;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getSourceType() {
            return SourceType;
        }

        public void setSourceType(int SourceType) {
            this.SourceType = SourceType;
        }

        public Object getSourceTypeName() {
            return SourceTypeName;
        }

        public void setSourceTypeName(Object SourceTypeName) {
            this.SourceTypeName = SourceTypeName;
        }

        public String getIp() {
            return Ip;
        }

        public void setIp(String Ip) {
            this.Ip = Ip;
        }

        public String getIpInfo() {
            return IpInfo;
        }

        public void setIpInfo(String IpInfo) {
            this.IpInfo = IpInfo;
        }

        public String getAgentIp() {
            return AgentIp;
        }

        public void setAgentIp(String AgentIp) {
            this.AgentIp = AgentIp;
        }

        public String getAgentIpInfo() {
            return AgentIpInfo;
        }

        public void setAgentIpInfo(String AgentIpInfo) {
            this.AgentIpInfo = AgentIpInfo;
        }

        public String getBrowserInfo() {
            return BrowserInfo;
        }

        public void setBrowserInfo(String BrowserInfo) {
            this.BrowserInfo = BrowserInfo;
        }

        public String getDomain() {
            return Domain;
        }

        public void setDomain(String Domain) {
            this.Domain = Domain;
        }

        public String getLoginTime() {
            return LoginTime;
        }

        public void setLoginTime(String LoginTime) {
            this.LoginTime = LoginTime;
        }

        public String getLastActionPageUrl() {
            return LastActionPageUrl;
        }

        public void setLastActionPageUrl(String LastActionPageUrl) {
            this.LastActionPageUrl = LastActionPageUrl;
        }

        public String getLastActionTime() {
            return LastActionTime;
        }

        public void setLastActionTime(String LastActionTime) {
            this.LastActionTime = LastActionTime;
        }

        public int getUserStatus() {
            return UserStatus;
        }

        public void setUserStatus(int UserStatus) {
            this.UserStatus = UserStatus;
        }

        public String getUserStatusName() {
            return UserStatusName;
        }

        public void setUserStatusName(String UserStatusName) {
            this.UserStatusName = UserStatusName;
        }

        public int getUserOnlineType() {
            return UserOnlineType;
        }

        public void setUserOnlineType(int UserOnlineType) {
            this.UserOnlineType = UserOnlineType;
        }

        public String getUserOnlineTypeName() {
            return UserOnlineTypeName;
        }

        public void setUserOnlineTypeName(String UserOnlineTypeName) {
            this.UserOnlineTypeName = UserOnlineTypeName;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
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

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
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

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateBy() {
            return UpdateBy;
        }

        public void setUpdateBy(String UpdateBy) {
            this.UpdateBy = UpdateBy;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
