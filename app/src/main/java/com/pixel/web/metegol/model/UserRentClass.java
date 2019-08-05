package com.pixel.web.metegol.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class UserRentClass {
    private String id_user="";
    private String mail="";
    private Number price=0.0;
    private Timestamp date_rent ;
    private Timestamp date_register ;

    public UserRentClass(String id_user, String mail, Number price, Timestamp date_rent, Timestamp date_register) {
        this.id_user = id_user;
        this.mail = mail;
        this.price = price;
        this.date_rent = date_rent;
        this.date_register = date_register;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Timestamp getDate_rent() {
        return date_rent;
    }

    public void setDate_rent(Timestamp date_rent) {
        this.date_rent = date_rent;
    }

    public Timestamp getDate_register() {
        return date_register;
    }

    public void setDate_register(Timestamp date_register) {
        this.date_register = date_register;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_user", this.id_user);
        result.put("mail", this.mail);
        result.put("price", this.price);
        result.put("date_rent", this.date_rent);
        result.put("date_register", this.date_register);

        return result;
    }

}
