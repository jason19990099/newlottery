package com.international.wtw.lottery.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuya on 2017/7/31.
 */

public class HistoryResult extends BaseModel {

    /**
     * result : [{"round":"20170731-43","endtime":"1501488600","number":"17,10,20,02,15,18,07,03"},{"round":"20170731-42","endtime":"1501488000","number":"09,18,02,16,08,20,01,12"},{"round":"20170731-41","endtime":"1501487400","number":"04,14,01,08,20,06,05,17"},{"round":"20170731-40","endtime":"1501486800","number":"11,06,01,04,18,19,15,05"},{"round":"20170731-39","endtime":"1501486200","number":"02,14,06,19,15,12,01,20"},{"round":"20170731-38","endtime":"1501485600","number":"07,10,14,02,03,15,13,19"},{"round":"20170731-37","endtime":"1501485000","number":"05,15,04,14,11,20,18,10"},{"round":"20170731-36","endtime":"1501484400","number":"20,03,10,01,02,14,05,16"},{"round":"20170731-35","endtime":"1501483800","number":"02,12,06,19,04,08,13,01"},{"round":"20170731-34","endtime":"1501483200","number":"07,04,19,03,08,15,11,12"},{"round":"20170731-33","endtime":"1501482600","number":"04,14,03,11,15,20,13,17"},{"round":"20170731-32","endtime":"1501482000","number":"08,14,03,16,07,19,15,01"},{"round":"20170731-31","endtime":"1501481400","number":"14,02,04,10,03,09,08,07"},{"round":"20170731-30","endtime":"1501480800","number":"10,01,19,05,03,16,08,07"},{"round":"20170731-29","endtime":"1501480200","number":"05,19,09,03,20,07,10,13"},{"round":"20170731-28","endtime":"1501479600","number":"08,14,20,11,06,10,12,17"},{"round":"20170731-27","endtime":"1501479000","number":"16,11,18,10,06,01,02,14"},{"round":"20170731-26","endtime":"1501478400","number":"09,16,10,06,04,05,08,03"},{"round":"20170731-25","endtime":"1501477800","number":"16,05,02,06,12,07,19,03"},{"round":"20170731-24","endtime":"1501477200","number":"11,19,14,15,07,03,17,09"}]
     * allnumb : 2000
     * page : 1
     * numb : 20
     */

    private int allnumb;
    private int page;
    private int numb;
    private List<ResultBean> result;

    public int getAllnumb() {
        return allnumb;
    }

    public void setAllnumb(int allnumb) {
        this.allnumb = allnumb;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumb() {
        return numb;
    }

    public void setNumb(int numb) {
        this.numb = numb;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * round : 20170731-43
         * endtime : 1501488600
         * number : 17,10,20,02,15,18,07,03
         */

        private String round;
        private String endtime;
        private String number;

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
