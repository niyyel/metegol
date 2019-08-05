package com.pixel.web.metegol.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class DetailRentClass {
    private  Number id_hour;
    private  String id_user_rent="";
    private  String state="";
    private String date_rent ="";
    private String id_user="";
    private String email="";

    public DetailRentClass() {
    }

    public DetailRentClass(Number id_hour, String id_user_rent, String state, String date_rent ,String id_user,String email) {
        this.id_hour = id_hour;
        this.id_user_rent = id_user_rent;
        this.state = state;
        this.date_rent = date_rent;
        this.id_user=id_user;
        this.email=email;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Number getId_hour() {
        return id_hour;
    }

    public void setId_hour(Number id_hour) {
        this.id_hour = id_hour;
    }

    public String getId_user_rent() {
        return id_user_rent;
    }

    public void setId_user_rent(String id_user_rent) {
        this.id_user_rent = id_user_rent;
    }

    public String getState() {
        return state;
    }
    public String getDate_rent() {
        return date_rent;
    }

    public void setDate_rent(String date_rent) {
        this.date_rent = date_rent;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_hour", this.id_hour);
        result.put("id_user_rent", this.id_user_rent);
        result.put("state", this.state);
        result.put("date_rent", this.date_rent);
        result.put("id_user", this.id_user);
        result.put("email", this.email);

        return result;
    }

}
