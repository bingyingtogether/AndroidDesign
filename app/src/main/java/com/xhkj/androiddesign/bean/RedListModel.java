package com.xhkj.androiddesign.bean;

import java.util.List;

/**
 * Created by xhkj_wjb on 2017/3/1.
 */

public class RedListModel {
    private String status;
    private String info;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class Data {
        private List<Red> red;

        public List<Red> getRed() {
            return red;
        }

        public void setRed(List<Red> red) {
            this.red = red;
        }

        public static class Red {
            private String red_id;
            private String red_name;
            private String red_img;
            private String is_top;
            private String is_recommend;
            private String red_surplus_money;
            private String firm_name;
            private String sort;
            private String check_time;
            private String add_time;
            private String red_type;

            public String getRed_id() {
                return red_id;
            }

            public void setRed_id(String red_id) {
                this.red_id = red_id;
            }

            public String getRed_name() {
                return red_name;
            }

            public void setRed_name(String red_name) {
                this.red_name = red_name;
            }

            public String getRed_img() {
                return red_img;
            }

            public void setRed_img(String red_img) {
                this.red_img = red_img;
            }

            public String getIs_top() {
                return is_top;
            }

            public void setIs_top(String is_top) {
                this.is_top = is_top;
            }

            public String getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(String is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getRed_surplus_money() {
                return red_surplus_money;
            }

            public void setRed_surplus_money(String red_surplus_money) {
                this.red_surplus_money = red_surplus_money;
            }

            public String getFirm_name() {
                return firm_name;
            }

            public void setFirm_name(String firm_name) {
                this.firm_name = firm_name;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getCheck_time() {
                return check_time;
            }

            public void setCheck_time(String check_time) {
                this.check_time = check_time;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getRed_type() {
                return red_type;
            }

            public void setRed_type(String red_type) {
                this.red_type = red_type;
            }
        }
    }

}
