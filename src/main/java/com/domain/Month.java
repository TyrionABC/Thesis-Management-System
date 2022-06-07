package com.domain;

public class Month {
    private String month;
    private Integer num;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Month{" +
                "month='" + month + '\'' +
                ", num=" + num +
                '}';
    }
}
