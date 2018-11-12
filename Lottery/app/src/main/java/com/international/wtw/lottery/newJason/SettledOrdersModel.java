package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class SettledOrdersModel extends BaseModel {
    private DataBeanX data;
    private int timestamp;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBeanX {
        private int amount;
        private double winamount;
        private List<DataBean> data;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public double getWinamount() {
            return winamount;
        }

        public void setWinamount(double winamount) {
            this.winamount = winamount;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * Id : 181018165957591618
             * SourceType : 4
             * AgentId : 1805201314000001
             * AgentName : shushu
             * Token : 0
             * UserId : 181005161602202088
             * Username : it0010
             * UserAccountId : 181005161602249089
             * AccountTypeId : 1
             * AccountTypeCode : XJ
             * AccountTypeName : 现金
             * GameTypeCode : msc
             * GameCode : msft
             * GameName : 秒速飞艇
             * PlayGroupCode : liangmianpan
             * PlayGroupName : 两面盘
             * PlayCode : no12he
             * PlayName : 冠、亚军和
             * PlayRateCode : shuang
             * PlayRateName : 冠亚双
             * PlayRateValueId : 180905112452890585
             * PlayRateValueType : 0
             * PlayRateValue : 2.19
             * PlayRebateValue : 1
             * ExpectNo : 181018457
             * Amount : 40
             * Deposit : 0
             * BetStatus : 6
             * BetStatusName : null
             * StopTime : -2209017600
             * OpenTime : 1539853191
             * OpenNo : null
             * BetResult : 1
             * BetResultName : null
             * WinLoseAmount : 47.6
             * RebateAmount : 0
             * ListGameCode : null
             * IsNoitfy : false
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
             * CreateTime : 1539853098
             * UpdateBy : 0
             * UpdateTime : -2209017600
             */

            private String Id;
            private int SourceType;
            private String AgentId;
            private String AgentName;
            private String Token;
            private String UserId;
            private String Username;
            private String UserAccountId;
            private int AccountTypeId;
            private String AccountTypeCode;
            private String AccountTypeName;
            private String GameTypeCode;
            private String GameCode;
            private String GameName;
            private String PlayGroupCode;
            private String PlayGroupName;
            private String PlayCode;
            private String PlayName;
            private String PlayRateCode;
            private String PlayRateName;
            private String PlayRateValueId;
            private int PlayRateValueType;
            private double PlayRateValue;
            private int PlayRebateValue;
            private String ExpectNo;
            private int Amount;
            private int Deposit;
            private int BetStatus;
            private Object BetStatusName;
            private long StopTime;
            private int OpenTime;
            private Object OpenNo;
            private int BetResult;
            private Object BetResultName;
            private double WinLoseAmount;
            private int RebateAmount;
            private Object ListGameCode;
            private boolean IsNoitfy;
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

            public int getSourceType() {
                return SourceType;
            }

            public void setSourceType(int SourceType) {
                this.SourceType = SourceType;
            }

            public String getAgentId() {
                return AgentId;
            }

            public void setAgentId(String AgentId) {
                this.AgentId = AgentId;
            }

            public String getAgentName() {
                return AgentName;
            }

            public void setAgentName(String AgentName) {
                this.AgentName = AgentName;
            }

            public String getToken() {
                return Token;
            }

            public void setToken(String Token) {
                this.Token = Token;
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

            public String getUserAccountId() {
                return UserAccountId;
            }

            public void setUserAccountId(String UserAccountId) {
                this.UserAccountId = UserAccountId;
            }

            public int getAccountTypeId() {
                return AccountTypeId;
            }

            public void setAccountTypeId(int AccountTypeId) {
                this.AccountTypeId = AccountTypeId;
            }

            public String getAccountTypeCode() {
                return AccountTypeCode;
            }

            public void setAccountTypeCode(String AccountTypeCode) {
                this.AccountTypeCode = AccountTypeCode;
            }

            public String getAccountTypeName() {
                return AccountTypeName;
            }

            public void setAccountTypeName(String AccountTypeName) {
                this.AccountTypeName = AccountTypeName;
            }

            public String getGameTypeCode() {
                return GameTypeCode;
            }

            public void setGameTypeCode(String GameTypeCode) {
                this.GameTypeCode = GameTypeCode;
            }

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

            public String getPlayGroupCode() {
                return PlayGroupCode;
            }

            public void setPlayGroupCode(String PlayGroupCode) {
                this.PlayGroupCode = PlayGroupCode;
            }

            public String getPlayGroupName() {
                return PlayGroupName;
            }

            public void setPlayGroupName(String PlayGroupName) {
                this.PlayGroupName = PlayGroupName;
            }

            public String getPlayCode() {
                return PlayCode;
            }

            public void setPlayCode(String PlayCode) {
                this.PlayCode = PlayCode;
            }

            public String getPlayName() {
                return PlayName;
            }

            public void setPlayName(String PlayName) {
                this.PlayName = PlayName;
            }

            public String getPlayRateCode() {
                return PlayRateCode;
            }

            public void setPlayRateCode(String PlayRateCode) {
                this.PlayRateCode = PlayRateCode;
            }

            public String getPlayRateName() {
                return PlayRateName;
            }

            public void setPlayRateName(String PlayRateName) {
                this.PlayRateName = PlayRateName;
            }

            public String getPlayRateValueId() {
                return PlayRateValueId;
            }

            public void setPlayRateValueId(String PlayRateValueId) {
                this.PlayRateValueId = PlayRateValueId;
            }

            public int getPlayRateValueType() {
                return PlayRateValueType;
            }

            public void setPlayRateValueType(int PlayRateValueType) {
                this.PlayRateValueType = PlayRateValueType;
            }

            public double getPlayRateValue() {
                return PlayRateValue;
            }

            public void setPlayRateValue(double PlayRateValue) {
                this.PlayRateValue = PlayRateValue;
            }

            public int getPlayRebateValue() {
                return PlayRebateValue;
            }

            public void setPlayRebateValue(int PlayRebateValue) {
                this.PlayRebateValue = PlayRebateValue;
            }

            public String getExpectNo() {
                return ExpectNo;
            }

            public void setExpectNo(String ExpectNo) {
                this.ExpectNo = ExpectNo;
            }

            public int getAmount() {
                return Amount;
            }

            public void setAmount(int Amount) {
                this.Amount = Amount;
            }

            public int getDeposit() {
                return Deposit;
            }

            public void setDeposit(int Deposit) {
                this.Deposit = Deposit;
            }

            public int getBetStatus() {
                return BetStatus;
            }

            public void setBetStatus(int BetStatus) {
                this.BetStatus = BetStatus;
            }

            public Object getBetStatusName() {
                return BetStatusName;
            }

            public void setBetStatusName(Object BetStatusName) {
                this.BetStatusName = BetStatusName;
            }

            public long getStopTime() {
                return StopTime;
            }

            public void setStopTime(long StopTime) {
                this.StopTime = StopTime;
            }

            public int getOpenTime() {
                return OpenTime;
            }

            public void setOpenTime(int OpenTime) {
                this.OpenTime = OpenTime;
            }

            public Object getOpenNo() {
                return OpenNo;
            }

            public void setOpenNo(Object OpenNo) {
                this.OpenNo = OpenNo;
            }

            public int getBetResult() {
                return BetResult;
            }

            public void setBetResult(int BetResult) {
                this.BetResult = BetResult;
            }

            public Object getBetResultName() {
                return BetResultName;
            }

            public void setBetResultName(Object BetResultName) {
                this.BetResultName = BetResultName;
            }

            public double getWinLoseAmount() {
                return WinLoseAmount;
            }

            public void setWinLoseAmount(double WinLoseAmount) {
                this.WinLoseAmount = WinLoseAmount;
            }

            public int getRebateAmount() {
                return RebateAmount;
            }

            public void setRebateAmount(int RebateAmount) {
                this.RebateAmount = RebateAmount;
            }

            public Object getListGameCode() {
                return ListGameCode;
            }

            public void setListGameCode(Object ListGameCode) {
                this.ListGameCode = ListGameCode;
            }

            public boolean isIsNoitfy() {
                return IsNoitfy;
            }

            public void setIsNoitfy(boolean IsNoitfy) {
                this.IsNoitfy = IsNoitfy;
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
}
