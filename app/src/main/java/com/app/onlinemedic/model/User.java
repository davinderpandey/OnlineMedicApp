package com.app.onlinemedic.model;

import com.orm.SugarRecord;

public class User extends SugarRecord {
    public int user_id;
    public String fname;
    public String lname;
    public String avatar;
    public String mobile;
    public String password;

    public User(){}

    public User(int user_id, String fname, String lname, String avatar, String mobile, String password){
        this.user_id = user_id;
        this.fname = fname;
        this.lname = lname;
        this.avatar = avatar;
        this.mobile = mobile;
        this.password = password;
    }
}
