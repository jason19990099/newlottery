package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class AllgameModel extends BaseModel {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Id : 2
         * GameTypeCode : msc
         * Name : 秒速时时彩
         * Code : msssc
         * IsClose : false
         * IsSystemClose : false
         * IsSystemDisable : false
         * CloseSecond : 15
         * OpenWaitSecond : 1
         * OpenTimes : 1105
         * UnitPrice : 0
         * MinMode : 0
         * MaxMode : 0
         * MaxPrize : 0
         * NumberRange : 0
         * NumberCount : 5
         * RegexExpectNo : null
         * RegexOpenNo : null
         * RateSetType : 1
         * AllowSourceType : ,1,2,3,4,5,6,
         * ExpectNoType : 1
         * ExpectNoBegin : 180915001
         * BeginTime : /Date(1536940800000)/
         * DataFormat : yyMMdd|001
         * IsDailyOpen : true
         * ImageUrl : null
         * ListGameRule : []
         * ListPlayGroup : null
         * ListPlayRateId : null
         * pageIndex : 1
         * pageSize : 10
         * pageSort : null
         * minAmount : 0
         * maxAmount : 0
         * beginTime : /Date(-2209017600000)/
         * endTime : /Date(-2209017600000)/
         * Sort : 3
         * IsDisable : false
         * CreateBy : 0
         * CreateTime : /Date(-2209017600000)/
         * UpdateBy : 0
         * UpdateTime : /Date(-2209017600000)/
         */

        private int Id;
        private String GameTypeCode;
        private String Name;
        private String Code;
        private boolean IsClose;
        private boolean IsSystemClose;
        private boolean IsSystemDisable;
        private int CloseSecond;
        private int OpenWaitSecond;
        private int OpenTimes;
        private int UnitPrice;
        private int MinMode;
        private int MaxMode;
        private int MaxPrize;
        private String NumberRange;
        private int NumberCount;
        private Object RegexExpectNo;
        private Object RegexOpenNo;
        private int RateSetType;
        private String AllowSourceType;
        private int ExpectNoType;
        private String ExpectNoBegin;
        private String BeginTime;
        private String DataFormat;
        private boolean IsDailyOpen;
        private Object ImageUrl;
        private Object ListPlayGroup;
        private Object ListPlayRateId;
        private int pageIndex;
        private int pageSize;
        private Object pageSort;
        private int minAmount;
        private int maxAmount;
        private String beginTime;
        private String endTime;
        private int Sort;
        private boolean IsDisable;
        private String CreateBy;
        private String CreateTime;
        private String UpdateBy;
        private String UpdateTime;
        private List<?> ListGameRule;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getGameTypeCode() {
            return GameTypeCode;
        }

        public void setGameTypeCode(String GameTypeCode) {
            this.GameTypeCode = GameTypeCode;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public boolean isIsClose() {
            return IsClose;
        }

        public void setIsClose(boolean IsClose) {
            this.IsClose = IsClose;
        }

        public boolean isIsSystemClose() {
            return IsSystemClose;
        }

        public void setIsSystemClose(boolean IsSystemClose) {
            this.IsSystemClose = IsSystemClose;
        }

        public boolean isIsSystemDisable() {
            return IsSystemDisable;
        }

        public void setIsSystemDisable(boolean IsSystemDisable) {
            this.IsSystemDisable = IsSystemDisable;
        }

        public int getCloseSecond() {
            return CloseSecond;
        }

        public void setCloseSecond(int CloseSecond) {
            this.CloseSecond = CloseSecond;
        }

        public int getOpenWaitSecond() {
            return OpenWaitSecond;
        }

        public void setOpenWaitSecond(int OpenWaitSecond) {
            this.OpenWaitSecond = OpenWaitSecond;
        }

        public int getOpenTimes() {
            return OpenTimes;
        }

        public void setOpenTimes(int OpenTimes) {
            this.OpenTimes = OpenTimes;
        }

        public int getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(int UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public int getMinMode() {
            return MinMode;
        }

        public void setMinMode(int MinMode) {
            this.MinMode = MinMode;
        }

        public int getMaxMode() {
            return MaxMode;
        }

        public void setMaxMode(int MaxMode) {
            this.MaxMode = MaxMode;
        }

        public int getMaxPrize() {
            return MaxPrize;
        }

        public void setMaxPrize(int MaxPrize) {
            this.MaxPrize = MaxPrize;
        }

        public String getNumberRange() {
            return NumberRange;
        }

        public void setNumberRange(String NumberRange) {
            this.NumberRange = NumberRange;
        }

        public int getNumberCount() {
            return NumberCount;
        }

        public void setNumberCount(int NumberCount) {
            this.NumberCount = NumberCount;
        }

        public Object getRegexExpectNo() {
            return RegexExpectNo;
        }

        public void setRegexExpectNo(Object RegexExpectNo) {
            this.RegexExpectNo = RegexExpectNo;
        }

        public Object getRegexOpenNo() {
            return RegexOpenNo;
        }

        public void setRegexOpenNo(Object RegexOpenNo) {
            this.RegexOpenNo = RegexOpenNo;
        }

        public int getRateSetType() {
            return RateSetType;
        }

        public void setRateSetType(int RateSetType) {
            this.RateSetType = RateSetType;
        }

        public String getAllowSourceType() {
            return AllowSourceType;
        }

        public void setAllowSourceType(String AllowSourceType) {
            this.AllowSourceType = AllowSourceType;
        }

        public int getExpectNoType() {
            return ExpectNoType;
        }

        public void setExpectNoType(int ExpectNoType) {
            this.ExpectNoType = ExpectNoType;
        }

        public String getExpectNoBegin() {
            return ExpectNoBegin;
        }

        public void setExpectNoBegin(String ExpectNoBegin) {
            this.ExpectNoBegin = ExpectNoBegin;
        }

        public String getBeginTime() {
            return BeginTime;
        }

        public void setBeginTime(String BeginTime) {
            this.BeginTime = BeginTime;
        }

        public String getDataFormat() {
            return DataFormat;
        }

        public void setDataFormat(String DataFormat) {
            this.DataFormat = DataFormat;
        }

        public boolean isIsDailyOpen() {
            return IsDailyOpen;
        }

        public void setIsDailyOpen(boolean IsDailyOpen) {
            this.IsDailyOpen = IsDailyOpen;
        }

        public Object getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(Object ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public Object getListPlayGroup() {
            return ListPlayGroup;
        }

        public void setListPlayGroup(Object ListPlayGroup) {
            this.ListPlayGroup = ListPlayGroup;
        }

        public Object getListPlayRateId() {
            return ListPlayRateId;
        }

        public void setListPlayRateId(Object ListPlayRateId) {
            this.ListPlayRateId = ListPlayRateId;
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

        public List<?> getListGameRule() {
            return ListGameRule;
        }

        public void setListGameRule(List<?> ListGameRule) {
            this.ListGameRule = ListGameRule;
        }
    }
}
