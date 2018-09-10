package com.international.wtw.lottery.json;

import java.io.Serializable;
import java.util.List;


public class NewOddsBean implements Serializable {
    /**
     * name : 冠军
     * list : [{"name":"大","odds":"1.988","key":"ip_3001_3011"},{"name":"小","odds":"1.988","key":"ip_3001_3012"},{"name":"单","odds":"1.988","key":"ip_3001_3013"},{"name":"双","odds":"1.988","key":"ip_3001_3014"},{"name":"龙","odds":"1.988","key":"ip_3001_3015"},{"name":"虎","odds":"1.988","key":"ip_3001_3016"}]
     */

    private String name;
    private List<ListBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 大
         * odds : 1.988
         * key : ip_3001_3011
         */
        private boolean selectedState = false; //标识当前Item是否被选中
        private String name;
        private String odds;
        private String key;
        private String type_name;

        public ListBean() {
        }

        public ListBean(String name, String key, boolean selectedState) {
            this.name = name;
            this.key = key;
            this.selectedState = selectedState;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOdds() {
            return odds;
        }

        public void setOdds(String odds) {
            this.odds = odds;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean getSelectedState() {
            return selectedState;
        }

        public void setSelectedState(boolean selectedState) {
            this.selectedState = selectedState;
        }
    }
}
