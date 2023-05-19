package com.app.onlinemedic.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class OrderInfo extends SugarRecord implements Serializable {
    public int id;
    public String name;
    public String order_date;
    public String order_time;
    public String order_desc;

    public OrderInfo(){}

    public OrderInfo(int id, String name, String order_date, String order_time, String order_desc){
        this.id = id;
        this.name = name;
        this.order_date = order_date;
        this.order_time = order_time;
        this.order_desc = order_desc;
    }
}
