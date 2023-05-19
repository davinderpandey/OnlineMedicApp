package com.app.onlinemedic.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class MedicineInfo extends SugarRecord implements Serializable {
    public int tab_id;
    public String tab_name;
    public String quantity;
    public String price;
    public String description;


    public MedicineInfo(){}

    public MedicineInfo(int tab_id, String tab_name, String quantity, String price, String description){
        this.tab_id = tab_id;
        this.tab_name = tab_name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }
}
