package com.example.accountingapplication.entity;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class Account implements Serializable, Comparable<Account> {
    private int id;
    private String sum;
    private String date;
    private String info;
    private String type;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int compareTo(Account o) {
        long time1 = new SimpleDateFormat("yyyy-MM-dd").parse(this.date, new ParsePosition(0)).getTime()/1000;
        long time2 = new SimpleDateFormat("yyyy-MM-dd").parse(o.getDate(), new ParsePosition(0)).getTime()/1000;

        if(time1 < time2){
            return 1;
        }else if (time1 > time2){
            return  -1;
        }else {
            return 0;
        }
    }
}
